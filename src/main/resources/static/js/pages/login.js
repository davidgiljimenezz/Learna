document.addEventListener("DOMContentLoaded", () => {
    const body = document.body;
    const container = document.querySelector(".container");
    const btnSignIn = document.querySelector("#btn-sign-in");
    const btnSignUp = document.querySelector("#btn-sign-up");

    const cambiarFormulario = (registro) => {
        container.classList.toggle("toggle", registro);
        body.classList.toggle("register-mode", registro);
        document.querySelector(".sign-in").inert = registro;
        document.querySelector(".sign-up").inert = !registro;
    };
    cambiarFormulario(container.classList.contains("toggle"));

    const mobileSignIn = document.querySelector("#mobile-sign-in");
    const mobileSignUp = document.querySelector("#mobile-sign-up");

    mobileSignIn.addEventListener("click", () => {
        cambiarFormulario(false);
    });

    mobileSignUp.addEventListener("click", () => {
        cambiarFormulario(true);
    });

    btnSignIn.addEventListener("click", () => {
        cambiarFormulario(false);
    });

    btnSignUp.addEventListener("click", () => {
        cambiarFormulario(true);
    });
});
