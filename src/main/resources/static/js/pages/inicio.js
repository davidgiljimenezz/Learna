const observador = new IntersectionObserver((entradas) => {
    entradas.forEach((entrada) => {
        if (entrada.isIntersecting) {
            entrada.target.classList.add("visible");
        }
    });
}, { threshold: 0.1 });

document.querySelectorAll(".animar").forEach((elemento) => {
    observador.observe(elemento);
});

const botonesCategorias = document.querySelectorAll(".cat");
const tarjetasCursos = document.querySelectorAll(".card");

botonesCategorias.forEach((boton) => {
    boton.addEventListener("click", () => {
        const categoria = boton.dataset.categoria;

        botonesCategorias.forEach((otroBoton) => {
            otroBoton.classList.remove("active");
        });

        boton.classList.add("active");

        tarjetasCursos.forEach((tarjeta) => {
            const categoriaCurso = tarjeta.dataset.categoria;
            const mostrar = categoria === "Todos" || categoriaCurso === categoria;
            tarjeta.style.display = mostrar ? "" : "none";
        });
    });
});
