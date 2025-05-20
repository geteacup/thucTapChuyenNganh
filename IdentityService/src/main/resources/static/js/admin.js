// Axios configuration
axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

// Configure axios defaults
axios.defaults.baseURL = '';
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('token')}`;

// Navigation
document.querySelectorAll('.nav-link').forEach(link => {
    if (!link.id || link.id !== 'logoutBtn') {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            document.querySelectorAll('.section').forEach(section => {
                section.classList.add('d-none');
            });
            document.querySelectorAll('.nav-link').forEach(navLink => {
                navLink.classList.remove('active');
            });
            const target = link.getAttribute('data-target');
            document.getElementById(target).classList.remove('d-none');
            link.classList.add('active');
            loadData(target);
        });
    }
});

// Logout
document.getElementById('logoutBtn').addEventListener('click', async (e) => {
    e.preventDefault();
    try {
        await axios.post('/auth/logout', {
            token: localStorage.getItem('token')
        });
    } catch (error) {
        console.error('Error logging out:', error);
    } finally {
        localStorage.removeItem('token');
        window.location.href = '/login';
    }
});

// Category CRUD
async function loadCategories() {
    try {
        const response = await axios.get('/loaisanpham');
        const categories = response.data.result;
        console.log('Categories:', categories); // Debug log
        const tbody = document.getElementById('categoryTable');
        tbody.innerHTML = categories.map(category => `
            <tr>
                <td>${category.maLoai}</td>
                <td>${category.tenLoai}</td>
                <td>
                    <button class="btn btn-sm btn-primary me-2" onclick="editCategory('${category.maLoai}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteCategory('${category.maLoai}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading categories:', error);
        alert('Có lỗi xảy ra khi tải danh sách loại sản phẩm');
    }
}

async function saveCategory() {
    const id = document.getElementById('categoryId').value;
    const name = document.getElementById('categoryName').value;
    
    try {
        if (id) {
            await axios.put(`/loaisanpham/${id}`, { tenLoai: name });
        } else {
            await axios.post('/loaisanpham', { tenLoai: name });
        }
        bootstrap.Modal.getInstance(document.getElementById('categoryModal')).hide();
        loadCategories();
    } catch (error) {
        console.error('Error saving category:', error);
        alert('Có lỗi xảy ra khi lưu loại sản phẩm');
    }
}

async function editCategory(id) {
    try {
        const response = await axios.get(`/loaisanpham/${id}`);
        const category = response.data.result;
        console.log('Category detail:', category); // Debug log
        document.getElementById('categoryId').value = category.maLoai;
        document.getElementById('categoryName').value = category.tenLoai;
        new bootstrap.Modal(document.getElementById('categoryModal')).show();
    } catch (error) {
        console.error('Error loading category:', error);
        alert('Có lỗi xảy ra khi tải thông tin loại sản phẩm');
    }
}

async function deleteCategory(id) {
    if (confirm('Bạn có chắc muốn xóa loại sản phẩm này?')) {
        try {
            await axios.delete(`/loaisanpham/${id}`);
            loadCategories();
        } catch (error) {
            console.error('Error deleting category:', error);
            alert('Có lỗi xảy ra khi xóa loại sản phẩm');
        }
    }
}

// Supplier CRUD
async function loadSuppliers() {
    try {
        const response = await axios.get('/nhacungcap');
        const suppliers = response.data.result;
        console.log('Suppliers:', suppliers); // Debug log
        const tbody = document.getElementById('supplierTable');
        tbody.innerHTML = suppliers.map(supplier => `
            <tr>
                <td>${supplier.maNCC}</td>
                <td>${supplier.tenNCC}</td>
                <td>${supplier.diaChi}</td>
                <td>${supplier.sdt}</td>
                <td>
                    <button class="btn btn-sm btn-primary me-2" onclick="editSupplier('${supplier.maNCC}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteSupplier('${supplier.maNCC}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading suppliers:', error);
        alert('Có lỗi xảy ra khi tải danh sách nhà cung cấp');
    }
}

async function saveSupplier() {
    const id = document.getElementById('supplierId').value;
    const data = {
        tenNCC: document.getElementById('supplierName').value,
        diaChi: document.getElementById('supplierAddress').value,
        sdt: document.getElementById('supplierPhone').value
    };
    
    try {
        if (id) {
            await axios.put(`/nhacungcap/${id}`, data);
        } else {
            await axios.post('/nhacungcap', data);
        }
        bootstrap.Modal.getInstance(document.getElementById('supplierModal')).hide();
        loadSuppliers();
    } catch (error) {
        console.error('Error saving supplier:', error);
        alert('Có lỗi xảy ra khi lưu nhà cung cấp');
    }
}

async function editSupplier(id) {
    try {
        const response = await axios.get(`/nhacungcap/${id}`);
        const supplier = response.data.result;
        console.log('Supplier detail:', supplier); // Debug log
        document.getElementById('supplierId').value = supplier.maNCC;
        document.getElementById('supplierName').value = supplier.tenNCC;
        document.getElementById('supplierAddress').value = supplier.diaChi;
        document.getElementById('supplierPhone').value = supplier.sdt;
        new bootstrap.Modal(document.getElementById('supplierModal')).show();
    } catch (error) {
        console.error('Error loading supplier:', error);
        alert('Có lỗi xảy ra khi tải thông tin nhà cung cấp');
    }
}

async function deleteSupplier(id) {
    if (confirm('Bạn có chắc muốn xóa nhà cung cấp này?')) {
        try {
            await axios.delete(`/nhacungcap/${id}`);
            loadSuppliers();
        } catch (error) {
            console.error('Error deleting supplier:', error);
            alert('Có lỗi xảy ra khi xóa nhà cung cấp');
        }
    }
}

// Product CRUD
async function loadProducts() {
    try {
        const response = await axios.get('/tonkho');
        const products = response.data.result;
        console.log('Products:', products); // Debug log
        const tbody = document.getElementById('productTable');
        tbody.innerHTML = products.map(product => `
            <tr>
                <td>${product.maSP || ''}</td>
                <td>${product.tenSP || ''}</td>
                <td>${product.giaSP ? product.giaSP.toLocaleString('vi-VN') : 0} VNĐ</td>
                <td>${product.soLuongSP || 0}</td>
                <td>${product.loaiSanPham ? product.loaiSanPham.tenLoai : ''}</td>
                <td>${product.nhaCungCap ? product.nhaCungCap.tenNCC : ''}</td>
                <td>
                    <button class="btn btn-sm btn-primary me-2" onclick="editProduct('${product.maSP}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteProduct('${product.maSP}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');

        // Load categories and suppliers for the product form
        loadCategoriesForSelect();
        loadSuppliersForSelect();
    } catch (error) {
        console.error('Error loading products:', error);
        alert('Có lỗi xảy ra khi tải danh sách sản phẩm');
    }
}

async function loadCategoriesForSelect() {
    try {
        const response = await axios.get('/loaisanpham');
        const categories = response.data.result;
        const select = document.getElementById('productCategory');
        select.innerHTML = '<option value="">Chọn loại sản phẩm</option>' + 
            categories.map(category => `
                <option value="${category.maLoai}">${category.tenLoai}</option>
            `).join('');
    } catch (error) {
        console.error('Error loading categories for select:', error);
    }
}

async function loadSuppliersForSelect() {
    try {
        const response = await axios.get('/nhacungcap');
        const suppliers = response.data.result;
        const select = document.getElementById('productSupplier');
        select.innerHTML = '<option value="">Chọn nhà cung cấp</option>' + 
            suppliers.map(supplier => `
                <option value="${supplier.maNCC}">${supplier.tenNCC}</option>
            `).join('');
    } catch (error) {
        console.error('Error loading suppliers for select:', error);
    }
}

async function saveProduct() {
    const id = document.getElementById('productId').value;
    const formData = new FormData();
    formData.append('tenSP', document.getElementById('productName').value);
    formData.append('giaSP', document.getElementById('productPrice').value);
    formData.append('soLuong', document.getElementById('productQuantity').value);
    formData.append('maLoai', document.getElementById('productCategory').value);
    formData.append('maNCC', document.getElementById('productSupplier').value);
    
    const imageFile = document.getElementById('productImage').files[0];
    if (imageFile) {
        formData.append('image', imageFile);
    }
    
    try {
        if (id) {
            await axios.put(`/tonkho/${id}`, formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });
        } else {
            await axios.post('/tonkho', formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });
        }
        bootstrap.Modal.getInstance(document.getElementById('productModal')).hide();
        loadProducts();
    } catch (error) {
        console.error('Error saving product:', error);
        alert('Có lỗi xảy ra khi lưu sản phẩm');
    }
}

async function editProduct(id) {
    try {
        const response = await axios.get(`/tonkho/${id}`);
        const product = response.data.result;
        console.log('Product detail:', product); // Debug log
        
        // Make sure categories and suppliers are loaded
        await Promise.all([loadCategoriesForSelect(), loadSuppliersForSelect()]);
        
        document.getElementById('productId').value = product.maSP;
        document.getElementById('productName').value = product.tenSP;
        document.getElementById('productPrice').value = product.giaSP;
        document.getElementById('productQuantity').value = product.soLuong;
        document.getElementById('productCategory').value = product.loaiSanPham ? product.loaiSanPham.maLoai : '';
        document.getElementById('productSupplier').value = product.nhaCungCap ? product.nhaCungCap.maNCC : '';
        
        new bootstrap.Modal(document.getElementById('productModal')).show();
    } catch (error) {
        console.error('Error loading product:', error);
        alert('Có lỗi xảy ra khi tải thông tin sản phẩm');
    }
}

async function deleteProduct(id) {
    if (confirm('Bạn có chắc muốn xóa sản phẩm này?')) {
        try {
            await axios.delete(`/tonkho/${id}`);
            loadProducts();
        } catch (error) {
            console.error('Error deleting product:', error);
            alert('Có lỗi xảy ra khi xóa sản phẩm');
        }
    }
}

// User CRUD
async function loadUsers() {
    try {
        const response = await axios.get('/users');
        const users = response.data.result;
        const tbody = document.getElementById('userTable');
        tbody.innerHTML = users.map(user => `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.roles ? user.roles.map(r => r.roleName).join(', ') : ''}</td>
                <td>
                    <button class="btn btn-sm btn-primary me-2" onclick="editUser('${user.id}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteUser('${user.id}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading users:', error);
        if (error.response && error.response.status === 401) {
            window.location.href = '/login';
        } else {
            alert('Có lỗi xảy ra khi tải danh sách người dùng');
        }
    }
}

// Load roles for user form
async function loadRolesForSelect() {
    try {
        const response = await axios.get('/roles');
        const roles = response.data.result;
        const select = document.getElementById('userRole');
        select.innerHTML = roles.map(role => `
            <option value="${role.roleName}">${role.roleName}</option>
        `).join('');
    } catch (error) {
        console.error('Error loading roles for select:', error);
    }
}

async function saveUser() {
    const id = document.getElementById('userId').value;
    const data = {
        username: document.getElementById('username').value,
        email: document.getElementById('userEmail').value,
        roles: Array.from(document.getElementById('userRole').selectedOptions).map(option => option.value)
    };
    
    const password = document.getElementById('userPassword').value;
    if (password) {
        data.password = password;
    }
    
    try {
        if (id) {
            await axios.put(`/users/${id}`, data);
        } else {
            await axios.post('/users', data);
        }
        bootstrap.Modal.getInstance(document.getElementById('userModal')).hide();
        loadUsers();
    } catch (error) {
        console.error('Error saving user:', error);
        alert('Có lỗi xảy ra khi lưu người dùng');
    }
}

async function editUser(id) {
    try {
        await loadRolesForSelect(); // Load roles first
        
        const response = await axios.get(`/users/${id}`);
        const user = response.data.result;
        document.getElementById('userId').value = user.id;
        document.getElementById('username').value = user.username;
        document.getElementById('userEmail').value = user.email;
        document.getElementById('userPassword').value = '';
        
        // Set selected roles
        const roleSelect = document.getElementById('userRole');
        const userRoles = user.roles || [];
        Array.from(roleSelect.options).forEach(option => {
            option.selected = userRoles.some(role => role.roleName === option.value);
        });
        
        new bootstrap.Modal(document.getElementById('userModal')).show();
    } catch (error) {
        console.error('Error loading user:', error);
        alert('Có lỗi xảy ra khi tải thông tin người dùng');
    }
}

async function deleteUser(id) {
    if (confirm('Bạn có chắc muốn xóa người dùng này?')) {
        try {
            await axios.delete(`/users/${id}`);
            loadUsers();
        } catch (error) {
            console.error('Error deleting user:', error);
            alert('Có lỗi xảy ra khi xóa người dùng');
        }
    }
}

// Role CRUD
async function loadRoles() {
    try {
        const response = await axios.get('/roles');
        const roles = response.data.result;
        console.log('Roles:', roles); // Debug log
        const tbody = document.getElementById('roleTable');
        tbody.innerHTML = roles.map(role => `
            <tr>
                <td>${role.roleName || ''}</td>
                <td>${role.description || ''}</td>
                <td>${role.permissionSet ? role.permissionSet.map(p => p.name).join(', ') : ''}</td>
                <td>
                    <button class="btn btn-sm btn-primary me-2" onclick="editRole('${role.roleName}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteRole('${role.roleName}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading roles:', error);
        if (error.response && error.response.status === 401) {
            window.location.href = '/login';
        } else {
            alert('Có lỗi xảy ra khi tải danh sách vai trò');
        }
    }
}

async function loadPermissions() {
    try {
        const response = await axios.get('/permissions');
        const permissions = response.data.result;
        console.log('Permissions:', permissions); // Debug log
        const container = document.getElementById('permissionCheckboxes');
        container.innerHTML = permissions.map(permission => `
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="permissions" value="${permission.name}" id="perm_${permission.name}">
                <label class="form-check-label" for="perm_${permission.name}">
                    ${permission.name} - ${permission.description || ''}
                </label>
            </div>
        `).join('');

        // Also update the permissions table if it exists
        const tbody = document.getElementById('permissionTable');
        if (tbody) {
            tbody.innerHTML = permissions.map(permission => `
                <tr>
                    <td>${permission.name}</td>
                    <td>${permission.description || ''}</td>
                    <td>
                        <button class="btn btn-sm btn-primary me-2" onclick="editPermission('${permission.name}')">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deletePermission('${permission.name}')">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading permissions:', error);
        alert('Có lỗi xảy ra khi tải danh sách quyền');
    }
}

async function editRole(roleName) {
    try {
        await loadPermissions(); // Load permissions first
        
        const response = await axios.get(`/roles/${roleName}`);
        const role = response.data.result;
        console.log('Role detail:', role); // Debug log
        
        document.getElementById('roleId').value = role.roleName;
        document.getElementById('roleName').value = role.roleName;
        document.getElementById('roleDescription').value = role.description || '';
        
        // Update permission checkboxes
        const permissionNames = role.permissionSet ? role.permissionSet.map(p => p.name) : [];
        document.querySelectorAll('input[name="permissions"]').forEach(cb => {
            cb.checked = permissionNames.includes(cb.value);
        });
        
        new bootstrap.Modal(document.getElementById('roleModal')).show();
    } catch (error) {
        console.error('Error loading role:', error);
        alert('Có lỗi xảy ra khi tải thông tin vai trò');
    }
}

async function saveRole() {
    const roleId = document.getElementById('roleId').value;
    const data = {
        roleName: document.getElementById('roleName').value,
        description: document.getElementById('roleDescription').value,
        permissionSet: Array.from(document.querySelectorAll('input[name="permissions"]:checked')).map(cb => cb.value)
    };
    
    try {
        if (roleId) {
            await axios.put(`/roles/${roleId}`, data);
        } else {
            await axios.post('/roles', data);
        }
        bootstrap.Modal.getInstance(document.getElementById('roleModal')).hide();
        loadRoles();
    } catch (error) {
        console.error('Error saving role:', error);
        alert('Có lỗi xảy ra khi lưu vai trò');
    }
}

async function editPermission(name) {
    try {
        const response = await axios.get(`/permissions/${name}`);
        const permission = response.data.result;
        console.log('Permission detail:', permission); // Debug log
        
        document.getElementById('permissionId').value = permission.name;
        document.getElementById('permissionName').value = permission.name;
        document.getElementById('permissionDescription').value = permission.description || '';
        
        new bootstrap.Modal(document.getElementById('permissionModal')).show();
    } catch (error) {
        console.error('Error loading permission:', error);
        alert('Có lỗi xảy ra khi tải thông tin quyền');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    loadCategories();
    loadUsers();
    loadRoles();
    loadPermissions();
});

// Event listeners for save buttons
document.getElementById('saveCategoryBtn').addEventListener('click', saveCategory);
document.getElementById('saveSupplierBtn').addEventListener('click', saveSupplier);
document.getElementById('saveProductBtn').addEventListener('click', saveProduct);
document.getElementById('saveUserBtn').addEventListener('click', saveUser);
document.getElementById('saveRoleBtn').addEventListener('click', saveRole);
document.getElementById('savePermissionBtn').addEventListener('click', savePermission);

// Modal reset handlers
['categoryModal', 'supplierModal', 'productModal', 'userModal', 'roleModal', 'permissionModal'].forEach(modalId => {
    document.getElementById(modalId).addEventListener('hidden.bs.modal', function () {
        this.querySelector('form').reset();
        this.querySelector('input[type="hidden"]').value = '';
    });
});

// Load initial data
loadCategories();

// Function to load data based on section
function loadData(section) {
    switch(section) {
        case 'categories':
            loadCategories();
            break;
        case 'suppliers':
            loadSuppliers();
            break;
        case 'products':
            loadProducts();
            break;
        case 'users':
            loadUsers();
            break;
        case 'roles':
            loadRoles();
            break;
        case 'permissions':
            loadPermissions();
            break;
    }
} 