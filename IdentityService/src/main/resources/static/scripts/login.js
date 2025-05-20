document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("loginForm").addEventListener("submit", async function (event) {
        event.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const response = await fetch("/identity/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({username, password})
        });

        if (response.ok) {
            const data = await response.json();
            const token = data.result.token;
            localStorage.setItem("access_token", token);
            fetch('/identity/home/success', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem("access_token")
                }
            })
                .then(res => {
                    if (res.redirected) {
                        // Nếu server trả về redirect (ví dụ 302)
                        window.location.href = res.url;
                    } else {
                        return res.text();
                    }
                })
                .then(html => {
                    // Hiển thị HTML trả về (nếu không redirect)
                    document.open();
                    document.write(html);
                    document.close();
                });
        } else {
            alert("Đăng nhập thất bại!");
        }
    });
});