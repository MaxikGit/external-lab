// Save scroll position when navigating away
window.addEventListener('beforeunload', () => {
    sessionStorage.setItem('scrollPosition', document.documentElement.scrollTop.toString());
    sessionStorage.setItem('itemsWereLoaded', currentItemNum.toString());
});

// Restore scroll position when returning to the page
window.addEventListener('load', () => {
    const scrollPositionThreshold = 10;
    const savedScrollPosition = Number(sessionStorage.getItem('scrollPosition'));
    if (savedScrollPosition !== null && savedScrollPosition > scrollPositionThreshold) {
        loadMoreItems();
        setTimeout(() => {
            document.documentElement.scrollTo(0, savedScrollPosition);
            sessionStorage.removeItem('scrollPosition');
        }, 100);
    }
});

//Load more items if it needed
const loadMoreItems = () =>{
    const itemsWereLoaded = Number(sessionStorage.getItem('itemsWereLoaded'));
    const itemsToLoad = itemsWereLoaded - currentItemNum;
    if(itemsToLoad > 0){
        loadItems(itemsToLoad);
        sessionStorage.removeItem('itemsWereLoaded');
    }
}