let backToTopButton;

document.addEventListener("DOMContentLoaded", () => {
    backToTopButton = document.getElementById("btn-back-to-top");
    window.addEventListener("scroll", manageBackToTopButton);
    backToTopButton.addEventListener("click", backToTop);
});

const manageBackToTopButton = () => {
    // When the user scrolls down 500px from the top of the document, show the button
    const activationDistance = 500;
    if (document.body.scrollTop > activationDistance || document.documentElement.scrollTop > activationDistance) {
        backToTopButton.classList.remove("d-none")
    } else if (!backToTopButton.classList.contains("d-none")) {
        backToTopButton.classList.add("d-none")
    }
}

const backToTop = () => {
    event.stopPropagation()
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}