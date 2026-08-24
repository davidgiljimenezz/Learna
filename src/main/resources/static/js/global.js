document.addEventListener("DOMContentLoaded", () => {
    const darkMode = document.querySelector(".dark-mode");

    if (darkMode) {
        darkMode.addEventListener("click", () => {
            document.body.classList.toggle("active");
        });
    }
});
