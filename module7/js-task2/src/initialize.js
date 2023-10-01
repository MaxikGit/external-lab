const cardLimit = 99;
const totalCategories = 5;
const categoryNames = [];
const initialLoad = 8;
let currentItemNum = 1;

document.addEventListener("DOMContentLoaded", () => {
    categoryNames.push(...generateCategoryNames());
    loadItems(initialLoad);
    addCategoryButtons();
    addCategoryThumbnails();
})

const createCard = (cardTitle, cardDescription, cardCategory) => {
    const cardContainer = document.getElementById("main-container");
    const parent = document.querySelector(".scrollable-item");
    const clone = parent.cloneNode(true);
    changeElementInnerText(clone, cardTitle, ".card-title");
    changeElementInnerText(clone, cardDescription, ".card-description");
    changeElementInnerText(clone, cardCategory, ".category-name");
    cardContainer.appendChild(clone);
}

const changeElementInnerText = (card, text, className) => {
    if (text === undefined || text === "") {
        return;
    }
    const element = card.querySelector(className);
    element.innerText = text;
}

const loadItems = (number) => {
    for (let i = 0; i < number; i++) {
        if (currentItemNum < cardLimit) {
            createCard(
                `Coupon Name${currentItemNum++}`,
                "",
                categoryNames[i % totalCategories]
            );
        }
    }
}

const addCategoryButtons = () => {
    const categoryAll = document.getElementById("category-all");
    categoryAll.addEventListener('click',
        () => filterCards("", [".category-name"]));
    const dropList = document.getElementById("category-dropdown");
    const firstCategoryLi = dropList.querySelector("li:last-child");
    for (let i = 0; i < totalCategories; i++) {
        const newCategoryLi = firstCategoryLi.cloneNode(true);
        const categoryButton = newCategoryLi.querySelector("button");
        categoryButton.textContent = categoryNames[i];
        categoryButton.addEventListener("click",
            () => filterCards(categoryNames[i], [".category-name"]));
        dropList.appendChild(newCategoryLi);
    }
    firstCategoryLi.remove();
}

const addCategoryThumbnails = () => {
    const thumbnailRow = document.getElementById("category-thumbnails");
    const firstCategoryDiv = thumbnailRow.querySelector("div:first-child");
    for (let i = 0; i < totalCategories; i++) {
        const newCategoryDiv = firstCategoryDiv.cloneNode(true);
        const categoryName = newCategoryDiv.querySelector("p");
        categoryName.textContent = categoryNames[i];
        thumbnailRow.appendChild(newCategoryDiv);
        newCategoryDiv.addEventListener("click",
            () => filterCards(categoryNames[i], [".category-name"]));
    }
    firstCategoryDiv.remove();
}

const generateCategoryNames = () => {
    const categoryNames = [];
    for (let i = 1; i <= totalCategories; i++) {
        categoryNames.push(`Category ${i}`);
    }
    return categoryNames;
}