// Kiểm tra trạng thái đăng nhập
let isLoggedIn = false;
let cart = [];

// Hàm kiểm tra trạng thái đăng nhập khi trang load
async function checkLoginStatus() {
    try {
        const response = await fetch('/identity/api/auth/status');
        const data = await response.json();
        isLoggedIn = data.isLoggedIn;
        updateNavRight();
        if (isLoggedIn) {
            loadCart();
        }
    } catch (error) {
        console.error('Error checking login status:', error);
    }
}

// Cập nhật giao diện nav-right dựa vào trạng thái đăng nhập
function updateNavRight() {
    const navRight = document.getElementById('nav-right');
    if (isLoggedIn) {
        navRight.innerHTML = `
            <button class="cart-btn" onclick="openCart()">
                <i class="fas fa-shopping-cart"></i>
                <span class="cart-count">0</span>
            </button>
            <button onclick="logout()" class="login-btn">Đăng xuất</button>
        `;
    } else {
        navRight.innerHTML = `
            <div class="auth-buttons">
                <button onclick="window.location.href='/identity/login'" class="login-btn">Đăng nhập</button>
                <button onclick="window.location.href='/identity/register'" class="register-btn">Đăng ký</button>
            </div>
        `;
    }
}

// Xử lý tìm kiếm
document.getElementById('search-btn').addEventListener('click', handleSearch);
document.getElementById('search-input').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        handleSearch();
    }
});

async function handleSearch() {
    const searchTerm = document.getElementById('search-input').value;
    try {
        const response = await fetch(`/identity/api/products/search?query=${encodeURIComponent(searchTerm)}`);
        const products = await response.json();
        displayProducts(products);
    } catch (error) {
        console.error('Error searching products:', error);
    }
}

// Hiển thị sản phẩm
async function loadProducts() {
    try {
        const response = await fetch('/identity/api/products');
        const products = await response.json();
        displayProducts(products);
    } catch (error) {
        console.error('Error loading products:', error);
    }
}

function displayProducts(products) {
    const productList = document.getElementById('product-list');
    productList.innerHTML = products.map(product => `
        <div class="product-card">
            <img src="${product.imageUrl}" alt="${product.name}">
            <h3>${product.name}</h3>
            <p>${product.price.toLocaleString('vi-VN')}đ</p>
            <button class="add-to-cart-btn" onclick="addToCart(${product.id})">
                Thêm vào giỏ hàng
            </button>
        </div>
    `).join('');
}

// Xử lý giỏ hàng
async function loadCart() {
    if (!isLoggedIn) return;
    try {
        const response = await fetch('/identity/api/cart');
        cart = await response.json();
        updateCartCount();
    } catch (error) {
        console.error('Error loading cart:', error);
    }
}

async function addToCart(productId) {
    if (!isLoggedIn) {
        alert('Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng!');
        return;
    }

    try {
        const response = await fetch('/identity/api/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ productId, quantity: 1 })
        });

        if (response.ok) {
            await loadCart();
            alert('Đã thêm sản phẩm vào giỏ hàng!');
        }
    } catch (error) {
        console.error('Error adding to cart:', error);
    }
}

function updateCartCount() {
    const cartCount = document.querySelector('.cart-count');
    if (cartCount) {
        cartCount.textContent = cart.reduce((total, item) => total + item.quantity, 0);
    }
}

// Modal giỏ hàng
const modal = document.getElementById('cart-modal');
const closeBtn = document.querySelector('.close');

function openCart() {
    if (!isLoggedIn) return;
    displayCart();
    modal.style.display = 'block';
}

function displayCart() {
    const cartItems = document.getElementById('cart-items');
    const totalAmount = document.getElementById('cart-total-amount');
    
    cartItems.innerHTML = cart.map(item => `
        <div class="cart-item">
            <img src="${item.product.imageUrl}" alt="${item.product.name}">
            <div class="cart-item-details">
                <h4>${item.product.name}</h4>
                <p>${item.product.price.toLocaleString('vi-VN')}đ x ${item.quantity}</p>
            </div>
            <div class="cart-item-actions">
                <button onclick="updateCartItem(${item.product.id}, ${item.quantity - 1})">-</button>
                <span>${item.quantity}</span>
                <button onclick="updateCartItem(${item.product.id}, ${item.quantity + 1})">+</button>
                <button onclick="removeFromCart(${item.product.id})">Xóa</button>
            </div>
        </div>
    `).join('');

    const total = cart.reduce((sum, item) => sum + (item.product.price * item.quantity), 0);
    totalAmount.textContent = total.toLocaleString('vi-VN') + 'đ';
}

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
        isLoggedIn = false;
        cart = [];
        updateNavRight();
        window.location.href = '/identity/login';
    } catch (error) {
        console.error('Error logging out:', error);
    }
}

// Load dữ liệu khi trang được tải
document.addEventListener('DOMContentLoaded', () => {
    checkLoginStatus();
    loadProducts();
}); 