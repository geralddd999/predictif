let currentIndex = 0;
let totalSlides = 0;
const VISIBLE_SLIDES = 3;
const SLIDE_STEP_PX = 230 + 24; // slide width + gap (must match CSS)
const AUTOPLAY_MS = 4000;
let autoplayTimer = null;


function initPage() {
    document.getElementById("login_btn").addEventListener('click', () => {
        window.location.href = "login.html";
    });
    document.getElementById("register_btn").addEventListener('click', () => {
        window.location.href = "register.html";
    });

    document.getElementById('prev-btn').addEventListener('click', () => moveCarousel(-1));
    document.getElementById('next-btn').addEventListener('click', () => moveCarousel(1));

    const carousel = document.getElementById('carousel');
    carousel.addEventListener('mouseenter', stopAutoplay);
    carousel.addEventListener('mouseleave', startAutoplay);
    fetchMediums();
}

async function fetchMediums() {
    try {
        const response = await fetch('/ActionServlet?todo=list-mediums');
        const data = await response.json();
        const container = document.getElementById("medium-carousel-container");
        container.innerHTML = ''; // placeholder
        data.forEach((medium) => container.appendChild(createMediumSlide(medium)));
        totalSlides = data.length;

        updateCarousel();
        startAutoplay();
    } catch (e) {
        console.error('Failed to load mediums:', e);
    }
}

function createMediumSlide(medium) {
    const slide = document.createElement('div');
    slide.className = 'slide';
    slide.setAttribute('role', 'tabpanel');

    const content = document.createElement('div');
    content.className = 'slide-content';

    const img = document.createElement('img');
    img.src = medium.image || ''; // TODO: hook up once API exposes image URLs
    img.alt = medium.name || '';
    img.onerror = () => { img.style.visibility = 'hidden'; };

    const caption = document.createElement('div');
    caption.className = 'slide-caption';

    const title = document.createElement('h3');
    title.textContent = medium.name;

    const desc = document.createElement('p');
    desc.textContent = medium.description;

    caption.appendChild(title);
    caption.appendChild(desc);


    content.appendChild(img);
    content.appendChild(caption);
    slide.appendChild(content);
    return slide;
}

function moveCarousel(direction) {
    if (totalSlides <= VISIBLE_SLIDES) return; // i have nothing
    const maxIndex = totalSlides - VISIBLE_SLIDES;
    currentIndex = (currentIndex + direction + totalSlides) % totalSlides;
    updateCarousel();

    if (autoplayTimer) startAutoplay();
}

function updateCarousel() {
    const track = document.getElementById('medium-carousel-container');
    track.style.transform = `translateX(-${currentIndex * SLIDE_STEP_PX}px)`;

    const prev = document.getElementById('prev-btn');
    const next = document.getElementById('next-btn');
    const maxIndex = Math.max(0, totalSlides - VISIBLE_SLIDES);
    prev.disabled = currentIndex === 0;
    next.disabled = currentIndex >= maxIndex;
}


function startAutoplay() {
    stopAutoplay();
    if (totalSlides <= VISIBLE_SLIDES) return;
    autoplayTimer = setInterval(() => {
        const maxIndex = totalSlides - VISIBLE_SLIDES;
        currentIndex = currentIndex >= maxIndex ? 0 : currentIndex + 1;
        updateCarousel();
    }, AUTOPLAY_MS);
}

function stopAutoplay() {
    if (autoplayTimer) {
        clearInterval(autoplayTimer);
        autoplayTimer = null;
    }
}

window.addEventListener('DOMContentLoaded', initPage);