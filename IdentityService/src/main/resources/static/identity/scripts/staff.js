// Biến toàn cục
let currentPage = 1;
let currentStatus = 'PENDING';
let currentSearchTerm = '';
let currentStartDate = '';
let currentEndDate = '';

// Kiểm tra đăng nhập và quyền truy cập
async function checkAuth() {
    try {
        const response = await fetch('/identity/api/auth/status');
        const data = await response.json();
        
        if (!data.isLoggedIn || data.role !== 'STAFF') {
            window.location.href = '/identity/login';
            return;
        }
        
        document.getElementById('staff-name').textContent = data.username;
    } catch (error) {
        console.error('Error checking auth:', error);
        window.location.href = '/identity/login';
    }
}

// Xử lý tabs
document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelector('.tab-btn.active').classList.remove('active');
        btn.classList.add('active');
        currentStatus = btn.dataset.status;
        currentPage = 1;
        loadOrders();
    });
});

// Xử lý tìm kiếm
document.getElementById('search-btn').addEventListener('click', handleSearch);
document.getElementById('search-input').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        handleSearch();
    }
});

function handleSearch() {
    currentSearchTerm = document.getElementById('search-input').value;
    currentPage = 1;
    loadOrders();
}

// Xử lý lọc theo ngày
document.getElementById('filter-btn').addEventListener('click', () => {
    currentStartDate = document.getElementById('start-date').value;
    currentEndDate = document.getElementById('end-date').value;
    currentPage = 1;
    loadOrders();
});

// Load danh sách đơn hàng
async function loadOrders() {
    try {
        const params = new URLSearchParams({
            page: currentPage - 1,
            size: 10,
            status: currentStatus,
            search: currentSearchTerm,
            startDate: currentStartDate,
            endDate: currentEndDate
        });

        const response = await fetch(`/identity/api/orders/staff?${params}`);
        const data = await response.json();
        
        displayOrders(data.content);
        updatePagination(data);
    } catch (error) {
        console.error('Error loading orders:', error);
    }
}

// Hiển thị danh sách đơn hàng
function displayOrders(orders) {
    const ordersList = document.getElementById('orders-list');
    
    if (orders.length === 0) {
        ordersList.innerHTML = '<p class="no-orders">Không có đơn hàng nào</p>';
        return;
    }

    ordersList.innerHTML = orders.map(order => `
        <div class="order-item" onclick="openOrderDetails(${order.id})">
            <div class="order-info">
                <h3>Đơn hàng #${order.id}</h3>
                <p>Khách hàng: ${order.customerName}</p>
                <p>Ngày đặt: ${new Date(order.orderDate).toLocaleDateString('vi-VN')}</p>
                <p>Tổng tiền: ${order.totalAmount.toLocaleString('vi-VN')}đ</p>
            </div>
            <span class="order-status status-${order.status.toLowerCase()}">${getStatusText(order.status)}</span>
        </div>
    `).join('');
}

// Cập nhật phân trang
function updatePagination(data) {
    document.getElementById('page-info').textContent = `Trang ${currentPage}/${data.totalPages}`;
    document.getElementById('prev-page').disabled = currentPage === 1;
    document.getElementById('next-page').disabled = currentPage === data.totalPages;
}

// Xử lý nút phân trang
document.getElementById('prev-page').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        loadOrders();
    }
});

document.getElementById('next-page').addEventListener('click', () => {
    currentPage++;
    loadOrders();
});

// Xử lý modal chi tiết đơn hàng
const modal = document.getElementById('order-modal');
const closeBtn = document.querySelector('.close');

async function openOrderDetails(orderId) {
    try {
        const response = await fetch(`/identity/api/orders/${orderId}`);
        const order = await response.json();
        
        const orderDetails = document.getElementById('order-details');
        orderDetails.innerHTML = `
            <div class="order-header">
                <h3>Đơn hàng #${order.id}</h3>
                <span class="order-status status-${order.status.toLowerCase()}">${getStatusText(order.status)}</span>
            </div>
            <div class="customer-info">
                <p><strong>Khách hàng:</strong> ${order.customerName}</p>
                <p><strong>Số điện thoại:</strong> ${order.phone}</p>
                <p><strong>Địa chỉ:</strong> ${order.address}</p>
                <p><strong>Ngày đặt:</strong> ${new Date(order.orderDate).toLocaleDateString('vi-VN')}</p>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                        <th>Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    ${order.items.map(item => `
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.quantity}</td>
                            <td>${item.price.toLocaleString('vi-VN')}đ</td>
                            <td>${(item.price * item.quantity).toLocaleString('vi-VN')}đ</td>
                        </tr>
                    `).join('')}
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3"><strong>Tổng cộng:</strong></td>
                        <td><strong>${order.totalAmount.toLocaleString('vi-VN')}đ</strong></td>
                    </tr>
                </tfoot>
            </table>
        `;

        // Hiển thị/ẩn các nút hành động dựa vào trạng thái đơn hàng
        updateActionButtons(order.status, orderId);
        
        modal.style.display = 'block';
    } catch (error) {
        console.error('Error loading order details:', error);
    }
}

// Cập nhật hiển thị các nút hành động
function updateActionButtons(status, orderId) {
    const acceptBtn = document.getElementById('accept-btn');
    const completeBtn = document.getElementById('complete-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    acceptBtn.style.display = status === 'PENDING' ? 'block' : 'none';
    completeBtn.style.display = status === 'PROCESSING' ? 'block' : 'none';
    cancelBtn.style.display = ['PENDING', 'PROCESSING'].includes(status) ? 'block' : 'none';

    acceptBtn.onclick = () => updateOrderStatus(orderId, 'PROCESSING');
    completeBtn.onclick = () => updateOrderStatus(orderId, 'COMPLETED');
    cancelBtn.onclick = () => updateOrderStatus(orderId, 'CANCELLED');
}

// Cập nhật trạng thái đơn hàng
async function updateOrderStatus(orderId, newStatus) {
    try {
        const response = await fetch(`/identity/api/orders/${orderId}/status`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ status: newStatus })
        });

        if (response.ok) {
            modal.style.display = 'none';
            loadOrders();
        }
    } catch (error) {
        console.error('Error updating order status:', error);
    }
}

// Chuyển đổi mã trạng thái sang text
function getStatusText(status) {
    const statusMap = {
        'PENDING': 'Chờ xử lý',
        'PROCESSING': 'Đang xử lý',
        'COMPLETED': 'Đã hoàn thành',
        'CANCELLED': 'Đã huỷ'
    };
    return statusMap[status] || status;
}

// Đóng modal
closeBtn.onclick = function() {
    modal.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}

// Xử lý đăng xuất
async function logout() {
    try {
        await fetch('/identity/api/auth/logout', { method: 'POST' });
        window.location.href = '/identity/login';
    } catch (error) {
        console.error('Error logging out:', error);
    }
}

// Khởi tạo trang
document.addEventListener('DOMContentLoaded', () => {
    checkAuth();
    loadOrders();
}); 