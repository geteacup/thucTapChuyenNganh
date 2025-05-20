
async function fetchDataTonKhoDefault() {

    try{
        var res = await fetch("/identity/tonkho", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const data = await res.json();
        return data;
    }
    catch (err) {
        const product = document.createElement('div');
        product.className = 'product';
        product.innerHTML = 'khong load duoc';
        const container = document.getElementById('product-list');
        container.appendChild(product);
        return null;
    }
}
async function fetchDataTonKhoLoaiSanPham(maLoai){
    try{
        var res = await fetch(`/identity/tonkho/loaisanpham/${maLoai}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const data = await res.json();
        return data;
    }
    catch (err) {
        const product = document.createElement('div');
        product.className = 'product';
        product.innerHTML = 'khong load duoc';
        const container = document.getElementById('product-list');
        container.appendChild(product);
        return null;
    }
}
async function fetchDataTonKhoNhaCungCap(maNCC){
    try{
        var res = await fetch(`/identity/tonkho/nhacungcap/${maNCC}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const data = await res.json();
        return data;
    }
    catch (err) {
        const product = document.createElement('div');
        product.className = 'product';
        product.innerHTML = 'khong load duoc';
        const container = document.getElementById('product-list');
        container.appendChild(product);
        return null;
    }
}
async function fetchDataNhaCungCapDefault(){
    try{
        var res = await fetch(`/identity/nhacungcap`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });
        const data = await res.json();
        return data;
    }
    catch (err) {
        const product = document.createElement('div');
        product.className = 'nha-cung-cap-item';
        product.innerHTML = 'khong load duoc';
        const container = document.getElementById('nha-cung-cap-list');
        container.appendChild(product);
        return null;
    }
}
async function fetchDataLoaiSanPhamDefault(){
    try{
        var res = await fetch(`/identity/loaisanpham`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });
        const data = await res.json();
        return data;
    }
    catch (err) {
        const product = document.createElement('div');
        product.className = 'loai-san-pham-item';
        product.innerHTML = 'khong load duoc';
        const container = document.getElementById('loai-san-pham-list');
        container.appendChild(product);
        return null;
    }
}
function loadProducts(data){
    const container = document.getElementById('product-list');
    container.innerHTML ='';

    data.result.forEach(sp => {
        const product = document.createElement('div');
        product.className = 'product';
        product.innerHTML = `
      <img   src="/identity/images/${sp.anhSP}" width="400" alt="${sp.anhSP}"/>
      <h3>${sp.tenSP}</h3>
      <p><strong>Mã:</strong> ${sp.maSP}</p>
      <p><strong>Giá:</strong> ${Number(sp.giaSP).toLocaleString()} VNĐ</p>
      <p><strong>Kích thước:</strong> ${sp.daiSP}m x ${sp.rongSP}m</p>
      <p><strong>Số lượng:</strong> ${sp.soLuongSP.toLocaleString()}</p>
    `;
        container.appendChild(product);
    });
}
function showError(message) {
    const container = document.getElementById('product-list');
    container.innerHTML = `<div class="product">${message}</div>`;
}

async function renderLoaiSanPhamButtons() {
    const data = await fetchDataLoaiSanPhamDefault();
    const container = document.getElementById('loai-san-pham-list');
    container.innerHTML = '';

    if (data && data.result) {
        data.result.forEach(loai => {
            const btn = document.createElement('button');
            btn.textContent = loai.tenLoai;
            btn.onclick = async () => {
                const spData = await fetchDataTonKhoLoaiSanPham(loai.maLoai);
                loadProducts(spData);
            };
            container.appendChild(btn);
        });
    }
}

async function renderNhaCungCapButtons() {
    const data = await fetchDataNhaCungCapDefault();
    const container = document.getElementById('nha-cung-cap-list');
    container.innerHTML = '';

    if (data && data.result) {
        data.result.forEach(ncc => {
            const btn = document.createElement('button');
            btn.textContent = ncc.tenNCC;
            btn.onclick = async () => {
                const spData = await fetchDataTonKhoNhaCungCap(ncc.maNCC);
                loadProducts(spData);
            };
            container.appendChild(btn);
        });
    }
}
document.getElementById('home-btn').onclick = async () => {
    const data = await fetchDataTonKhoDefault();
    loadProducts(data);
};
// Khi trang load xong
(async function () {
    const data = await fetchDataTonKhoDefault();
    loadProducts(data);
    renderLoaiSanPhamButtons();
    renderNhaCungCapButtons();
})();