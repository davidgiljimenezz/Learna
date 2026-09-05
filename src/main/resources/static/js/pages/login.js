document.addEventListener("DOMContentLoaded", () => {
    const body = document.body;
    const container = document.querySelector(".container");
    const btnSignIn = document.querySelector("#btn-sign-in");
    const btnSignUp = document.querySelector("#btn-sign-up");
    const formularioRegistro = document.querySelector(".sign-up");
    const passwordRegister = document.querySelector("#password-register");
    const errorRegister = document.querySelector("#error-register");
    const formularioLogin = document.querySelector(".sign-in");
    const passwordLogin = document.querySelector("#password-login");
    const errorLogin = document.querySelector("#error-login");

    const mobileSignIn = document.querySelector("#mobile-sign-in");
    const mobileSignUp = document.querySelector("#mobile-sign-up");

    mobileSignIn.addEventListener("click", () => {
        container.classList.remove("toggle");
        body.classList.remove("register-mode");
    });

    mobileSignUp.addEventListener("click", () => {
        container.classList.add("toggle");
        body.classList.add("register-mode");
    });

    btnSignIn.addEventListener("click", () => {
        container.classList.remove("toggle");
        body.classList.remove("register-mode");
    });

    btnSignUp.addEventListener("click", () => {
        container.classList.add("toggle");
        body.classList.add("register-mode");
    });
    formularioRegistro.addEventListener("submit", (event) => {
        event.preventDefault();

        if (passwordRegister.value.length < 8 || passwordRegister.value.length > 16) {
            errorRegister.textContent = "La contraseña debe tener entre 8 y 16 caracteres.";
            return;
        }

        errorRegister.textContent = "";
        window.location.href = "/index";
    });

    formularioLogin.addEventListener("submit", (event) => {
        event.preventDefault();

        if (passwordLogin.value.length < 8 || passwordLogin.value.length > 16) {
            errorLogin.textContent = "La contraseña debe tener entre 8 y 16 caracteres.";
            return;
        }

        errorLogin.textContent = "";
        window.location.href = "/index";
    });
});
