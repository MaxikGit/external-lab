// this script uses searchInProgress variable from the live_search.js script

const cardIncrease = 5;
const delay = 1000;
let debounceTimer;

document.addEventListener("DOMContentLoaded", () => {
    window.addEventListener("scroll", handleInfiniteScroll);
})

let progressBarDiv;
const handleInfiniteScroll = () => {
    if (searchInProgress) {
        return;
    }
    const threshold = 5;
    const {scrollHeight, scrollTop, clientHeight} = document.documentElement;
    if (scrollTop + clientHeight > scrollHeight - threshold) {
        if (!progressBarDiv) {
            progressBarDiv = createProgressBar();
        }
        debounce(() => {
                loadItems(cardIncrease);
                if (progressBarDiv) {
                    progressBarDiv.remove();
                    progressBarDiv = null;
                }
            },
            delay);
    }
}

const debounce = (callback, time) => {
    if (debounceTimer) {
        return;
    }
    debounceTimer = true;
    setTimeout(() => {
        callback();
        debounceTimer = false;
    }, time);
}

const createProgressBar = () => {
    const initDiv = document.createElement("div");
    initDiv.classList.add("progress", "w-100");
    const progressBar = document.createElement("div");
    progressBar.classList.add("progress-bar", "fixed-bottom", "bg-lightgreen");
    progressBar.setAttribute("role", "progressbar");
    progressBar.setAttribute("aria-valuenow", "0");
    progressBar.setAttribute("aria-valuemin", "0");
    progressBar.setAttribute("aria-valuemax", "100");
    progressBar.style.width = "0%"; // Initial width
    initDiv.appendChild(progressBar);
    document.body.appendChild(initDiv);

    const progressStep = 10;
    let currentProgress = progressStep;
    const updateInterval = setInterval(() => {
        if (currentProgress <= 100) {
            updateProgressBar(currentProgress);
            currentProgress += progressStep;
        } else {
            clearInterval(updateInterval);
        }
    }, (delay - delay / 2) * (progressStep / 100));

    function updateProgressBar(percentage) {
        progressBar.style.width = percentage + "%";
        progressBar.setAttribute("aria-valuenow", percentage);
        progressBar.textContent = percentage + "%";
    }

    return initDiv;
}