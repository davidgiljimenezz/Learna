document.addEventListener("DOMContentLoaded", () => {
    const normalizarRuta = (ruta) => ruta.replace(/\/$/, "") || "/";
    const rutaActual = normalizarRuta(window.location.pathname);

    document.querySelectorAll(".toolbar a[href]").forEach((enlace) => {
        const rutaEnlace = normalizarRuta(new URL(enlace.href).pathname);
        if (rutaEnlace === rutaActual || (rutaActual === "/index" && rutaEnlace === "/")) {
            enlace.setAttribute("aria-current", "page");
        } else {
            enlace.removeAttribute("aria-current");
        }
    });

    const darkMode = document.querySelector(".dark-mode");
    const temaGuardado = localStorage.getItem("modoOscuro");

    if (temaGuardado === "true") {
        document.body.classList.add("active");
    }

    if (darkMode) {
        darkMode.addEventListener("click", () => {
            document.body.classList.toggle("active");
            const modoOscuro = document.body.classList.contains("active");
            localStorage.setItem("modoOscuro", modoOscuro);
        });
    }
});
