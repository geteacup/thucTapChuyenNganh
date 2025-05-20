// Biến toàn cục
let currentTable = 'users';
let currentPage = 1;
let currentSearchTerm = '';
let currentItem = null;
let deleteItemId = null;

// Cấu hình các bảng
const tableConfigs = {
    users: {
        title: 'Quản lý người dùng',
        endpoint: '/identity/api/users',
        columns: [
            { field: 'id', header: 'ID' },
            { field: 'username', header: 'Tên đăng nhập' },
            { field: 'email', header: 'Email' },
            { field: 'role', header: 'Vai trò' },
            { field: 'status', header: 'Trạng thái' }
        ],
        formFields: [
            { name: 'username', label: 'Tên đăng nhập', type: 'text', required: true },
            { name: 'email', label: 'Email', type: 'email', required: true },
            { name: 'password', label: 'Mật khẩu', type: 'password', required: true },
            { 
                name: 'role', 
                label: 'Vai trò', 
                type: 'select', 
                options: [
                    { value: 'USER', label: 'Người dùng' },
                    { value: 'STAFF', label: 'Nhân viên' },
                    { value: 'ADMIN', label: 'Quản trị viên' }
                ],
                required: true 
            },
            {
                name: 'status',
                label: 'Trạng thái',
                type: 'select',
                options: [
                    { value: 'ACTIVE', label: 'Hoạt động' },
                    { value: 'INACTIVE', label: 'Không hoạt động' }
                ],
                required: true
            }
        ]
    },
    products: {
        title: 'Quản lý sản phẩm',
        endpoint: '/identity/api/products',
        columns: [
            { field: 'id', header: 'ID' },
            { field: 'name', header: 'Tên sản phẩm' },
            { field: 'price', header: 'Giá' },
            { field: 'category', header: 'Danh mục' },
            { field: 'supplier', header: 'Nhà cung cấp' },
            { field: 'stock', header: 'Tồn kho' }
        ],
        formFields: [
            { name: 'name', label: 'Tên sản phẩm', type: 'text', required: true },
            { name: 'price', label: 'Giá', type: 'number', required: true },
            { name: 'description', label: 'Mô tả', type: 'textarea' },
            { name: 'imageUrl', label: 'URL hình ảnh', type: 'text' },
            { name: 'categoryId', label: 'Danh mục', type: 'select', required: true, dynamicOptions: 'categories' },
            { name: 'supplierId', label: 'Nhà cung cấp', type: 'select', required: true, dynamicOptions: 'suppliers' },
            { name: 'stock', label: 'Tồn kho', type: 'number', required: true }
        ]
    },
    categories: {
        title: 'Quản lý danh mục',
        endpoint: '/identity/api/categories',
        columns: [
            { field: 'id', header: 'ID' },
            { field: 'name', header: 'Tên danh mục' },
            { field: 'description', header: 'Mô tả' }
        ],
        formFields: [
            { name: 'name', label: 'Tên danh mục', type: 'text', required: true },
            { name: 'description', label: 'Mô tả', type: 'textarea' }
        ]
    },
    suppliers: {
        title: 'Quản lý nhà cung cấp',
        endpoint: '/identity/api/suppliers',
        columns: [
            { field: 'id', header: 'ID' },
            { field: 'name', header: 'Tên nhà cung cấp' },
            { field: 'email', header: 'Email' },
            { field: 'phone', header: 'Số điện thoại' },
            { field: 'address', header: 'Địa chỉ' }
        ],
        formFields: [
            { name: 'name', label: 'Tên nhà cung cấp', type: 'text', required: true },
            { name: 'email', label: 'Email', type: 'email', required: true },
            { name: 'phone', label: 'Số điện thoại', type: 'tel', required: true },
            { name: 'address', label: 'Địa chỉ', type: 'textarea', required: true }
        ]
    },
    orders: {
        title: 'Quản lý đơn hàng',
        endpoint: '/identity/api/orders',
        columns: [
            { field: 'id', header: 'ID' },
            { field: 'customerName', header: 'Khách hàng' },
            { field: 'orderDate', header: 'Ngày đặt' },
            { field: 'totalAmount', header: 'Tổng tiền' },
            { field: 'status', header: 'Trạng thái' }
        ],
        formFields: [
            { name: 'customerName', label: 'Tên khách hàng', type: 'text', required: true },
            { name: 'phone', label: 'Số điện thoại', type: 'tel', required: true },
            { name: 'address', label: 'Địa chỉ', type: 'textarea', required: true },
            {
                name: 'status',
                label: 'Trạng thái',
                type: 'select',
                options: [
                    { value: 'PENDING', label: 'Chờ xử lý' },
                    { value: 'PROCESSING', label: 'Đang xử lý' },
                    { value: 'COMPLETED', label: 'Đã hoàn thành' },
                    { value: 'CANCELLED', label: 'Đã huỷ' }
                ],
                required: true
            }
        ]
    }
};

// Kiểm tra đăng nhập và quyền truy cập
async function checkAuth() {
    try {
        const response = await fetch('/identity/api/auth/status');
        const data = await response.json();
        
        if (!data.isLoggedIn || data.role !== 'ADMIN') {
            window.location.href = '/identity/login';
            return;
        }
    } catch (error) {
        console.error('Error checking auth:', error);
        window.location.href = '/identity/login';
    }
}

// Xử lý chuyển đổi bảng
document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', () => {
        document.querySelector('.nav-item.active').classList.remove('active');
        item.classList.add('active');
        currentTable = item.dataset.table;
        currentPage = 1;
        currentSearchTerm = '';
        document.getElementById('search-input').value = '';
        updateTableTitle();
        loadData();
    });
});

// Cập nhật tiêu đề bảng
function updateTableTitle() {
    document.getElementById('table-title').textContent = tableConfigs[currentTable].title;
}

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
    loadData();
}

// Load dữ liệu
async function loadData() {
    try {
        const params = new URLSearchParams({
            page: currentPage - 1,
            size: 10,
            search: currentSearchTerm
        });

        const response = await fetch(`${tableConfigs[currentTable].endpoint}?${params}`);
        const data = await response.json();
        
        displayData(data.content);
        updatePagination(data);
    } catch (error) {
        console.error('Error loading data:', error);
    }
}

// Hiển thị dữ liệu
function displayData(items) {
    const table = document.getElementById('data-table');
    const columns = tableConfigs[currentTable].columns;

    // Tạo header
    const thead = table.querySelector('thead');
    thead.innerHTML = `
        <tr>
            ${columns.map(col => `<th>${col.header}</th>`).join('')}
            <th>Thao tác</th>
        </tr>
    `;

    // Tạo body
    const tbody = table.querySelector('tbody');
    if (items.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="${columns.length + 1}" style="text-align: center;">Không có dữ liệu</td>
            </tr>
        `;
        return;
    }

    tbody.innerHTML = items.map(item => `
        <tr>
            ${columns.map(col => `
                <td>${formatValue(item[col.field], col.field)}</td>
            `).join('')}
            <td>
                <div class="action-buttons">
                    <button class="edit-btn" onclick="openEditModal(${item.id})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="delete-btn" onclick="openDeleteModal(${item.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

// Format giá trị hiển thị
function formatValue(value, field) {
    if (value === null || value === undefined) return '';
    
    if (field === 'price' || field === 'totalAmount') {
        return value.toLocaleString('vi-VN') + 'đ';
    }
    
    if (field === 'orderDate') {
        return new Date(value).toLocaleDateString('vi-VN');
    }
    
    if (field === 'status') {
        return `<span class="status-badge status-${value.toLowerCase()}">${getStatusText(value)}</span>`;
    }
    
    return value;
}

// Chuyển đổi mã trạng thái sang text
function getStatusText(status) {
    const statusMap = {
        'ACTIVE': 'Hoạt động',
        'INACTIVE': 'Không hoạt động',
        'PENDING': 'Chờ xử lý',
        'PROCESSING': 'Đang xử lý',
        'COMPLETED': 'Đã hoàn thành',
        'CANCELLED': 'Đã huỷ'
    };
    return statusMap[status] || status;
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
        loadData();
    }
});

document.getElementById('next-page').addEventListener('click', () => {
    currentPage++;
    loadData();
});

// Modal thêm/sửa
const crudModal = document.getElementById('crud-modal');
const crudForm = document.getElementById('crud-form');

function openAddModal() {
    currentItem = null;
    document.getElementById('modal-title').textContent = 'Thêm mới';
    buildForm();
    crudModal.style.display = 'block';
}

async function openEditModal(id) {
    try {
        const response = await fetch(`${tableConfigs[currentTable].endpoint}/${id}`);
        currentItem = await response.json();
        document.getElementById('modal-title').textContent = 'Chỉnh sửa';
        buildForm();
        crudModal.style.display = 'block';
    } catch (error) {
        console.error('Error loading item:', error);
    }
}

function closeModal() {
    crudModal.style.display = 'none';
    currentItem = null;
    crudForm.reset();
}

// Tạo form động
async function buildForm() {
    const formFields = tableConfigs[currentTable].formFields;
    let formHtml = '';

    for (const field of formFields) {
        let options = field.options;
        
        // Load options động nếu cần
        if (field.dynamicOptions) {
            try {
                const response = await fetch(`/identity/api/${field.dynamicOptions}`);
                const data = await response.json();
                options = data.map(item => ({
                    value: item.id,
                    label: item.name
                }));
            } catch (error) {
                console.error(`Error loading ${field.dynamicOptions}:`, error);
                options = [];
            }
        }

        formHtml += `
            <div class="form-group">
                <label for="${field.name}">${field.label}${field.required ? ' *' : ''}</label>
                ${getFormFieldHtml(field, options)}
            </div>
        `;
    }

    crudForm.innerHTML = formHtml;

    // Điền giá trị nếu là chỉnh sửa
    if (currentItem) {
        formFields.forEach(field => {
            const input = document.getElementById(field.name);
            if (input) {
                input.value = currentItem[field.name] || '';
            }
        });
    }
}

// Tạo HTML cho từng loại input
function getFormFieldHtml(field, options) {
    switch (field.type) {
        case 'select':
            return `
                <select id="${field.name}" name="${field.name}" ${field.required ? 'required' : ''}>
                    <option value="">Chọn ${field.label.toLowerCase()}</option>
                    ${options.map(opt => `
                        <option value="${opt.value}">${opt.label}</option>
                    `).join('')}
                </select>
            `;
        
        case 'textarea':
            return `
                <textarea id="${field.name}" name="${field.name}" 
                    ${field.required ? 'required' : ''}></textarea>
            `;
        
        default:
            return `
                <input type="${field.type}" id="${field.name}" name="${field.name}"
                    ${field.required ? 'required' : ''}>
            `;
    }
}

// Lưu dữ liệu
async function saveData() {
    const formData = new FormData(crudForm);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch(tableConfigs[currentTable].endpoint + (currentItem ? `/${currentItem.id}` : ''), {
            method: currentItem ? 'PUT' : 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            closeModal();
            loadData();
        }
    } catch (error) {
        console.error('Error saving data:', error);
    }
}

// Modal xoá
const deleteModal = document.getElementById('delete-modal');

function openDeleteModal(id) {
    deleteItemId = id;
    deleteModal.style.display = 'block';
}

function closeDeleteModal() {
    deleteModal.style.display = 'none';
    deleteItemId = null;
}

async function confirmDelete() {
    if (!deleteItemId) return;

    try {
        const response = await fetch(`${tableConfigs[currentTable].endpoint}/${deleteItemId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            closeDeleteModal();
            loadData();
        }
    } catch (error) {
        console.error('Error deleting item:', error);
    }
}

// Đóng modal khi click bên ngoài
window.onclick = function(event) {
    if (event.target == crudModal) {
        closeModal();
    }
    if (event.target == deleteModal) {
        closeDeleteModal();
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
    updateTableTitle();
    loadData();
}); 