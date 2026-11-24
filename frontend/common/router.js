// Maps a hash route to the id of the section that shows it.
// New features add one line here.
const ROUTES = {
    '/': 'home-view'
};

const NOT_FOUND_VIEW = 'not-found-view';

function getCurrentRoute() {
    return window.location.hash.slice(1) || '/';
}


function showView(viewId) {
    document.querySelectorAll('#app > section').forEach(section => {
        section.hidden = section.id !== viewId;
    });
}

function router() {
    const viewId = ROUTES[getCurrentRoute()] ?? NOT_FOUND_VIEW;
    showView(viewId);
}

window.addEventListener('hashchange', router);
router();