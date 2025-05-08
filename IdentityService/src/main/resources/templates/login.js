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

            alert("Đăng nhập thành công!");
            window.location.href = "/identity/home/success";
        } else {
            alert("Đăng nhập thất bại!");
        }
    });
});