document.addEventListener("DOMContentLoaded", () => {

    //key is search input id, value - card element className to find
    const searchNameElementName = new Map();
    searchNameElementName.set("top-search-field", [".card-title", ".card-description"]);
    searchNameElementName.set("tag-search-box", [".category-name"]);

    searchNameElementName.forEach((value, key) => {
        const searchBox = document.getElementById(key);
        searchBox.addEventListener("input", () => liveSearch(searchBox, value));
    })
});

let searchInProgress; //don't rename - that flag is used in scroll.js
let typingTagTimer;

const liveSearch = (searchBox, cardElementClassNames) => {
    const typeInterval = 500;
    clearTimeout(typingTagTimer);
    typingTagTimer = setTimeout(() => {
        let searchKey = searchBox.value;
        filterCards(searchKey, cardElementClassNames);
    }, typeInterval);
}

const filterCards = (keyWord, cardElementClassNames) => {
    searchInProgress = keyWord !== "";
    let cards = document.querySelectorAll(".scrollable-item")
    for (let i = 0; i < cards.length; i++) {
        let isVisible = false;
        let j = 0;
        do {
            const cardElement = cards[i].querySelector(cardElementClassNames[j]);
            const cardElementText = cardElement.innerText.trim().toLowerCase();
            isVisible += cardElementText.includes(keyWord.toLowerCase());
        }
        while (++j < cardElementClassNames.length && !isVisible);
        toggleCard(cards[i], isVisible);
    }
}

const toggleCard = (card, isVisible) => {
    if (isVisible) {
        card.classList.remove("d-none");
    } else {
        if (!card.classList.contains("d-none")) {
            card.classList.add("d-none");
        }
        if (!card.classList.contains("d-none")) {
            card.classList.add("d-none");
        }
    }
}
