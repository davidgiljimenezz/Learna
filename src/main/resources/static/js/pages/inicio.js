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
