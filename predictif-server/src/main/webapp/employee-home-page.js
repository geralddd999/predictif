let pageData = null;


async function initPage() {
  try {
    const res = await fetch('/predictif-server/ActionServlet?todo=consult-employee-rdv');
    const data = await res.json();

    if (data.error) {
      if (data.redirect) window.location.href = data.redirect;
      return;
    }

    const { client_astral_profile, client_history, medium } = data;

    if (!client_astral_profile || !medium) {
      renderNoRDV();
      return;
    }

    pageData = {
      astral: client_astral_profile,
      history: client_history || [],
      medium,
    };
    renderPrepStage();
  } catch (e) {
    console.error('Failed to load RDV data', e);
  }
}


function renderNoRDV() {
  const main = document.getElementById('main-content');
  main.className = 'main-content stage-empty';
  main.innerHTML = '';

  const msg = document.createElement('p');
  msg.id = 'no-rdv-msg';
  msg.textContent = 'Aucun rendez-vous actif.';
  main.appendChild(msg);

  document.getElementById('start-rdv-btn').style.display = 'none';
}

function renderPrepStage() {
  const main = document.getElementById('main-content');
  main.className = 'main-content stage-prep';
  main.innerHTML = '';

  document.getElementById('page-title').textContent = 'Votre rendez-vous';

  const grid = document.createElement('div');
  grid.id = 'cards-grid';
  grid.className = 'cards-grid';
  grid.appendChild(buildAstralCard(pageData.astral));
  grid.appendChild(buildHistoryCard(pageData.history));
  grid.appendChild(buildMediumCard(pageData.medium));
  main.appendChild(grid);

  const btn = document.getElementById('start-rdv-btn');
  btn.textContent = 'Prêt(e)';
  btn.className = 'btn btn-primary btn-action';
  btn.style.display = '';
  btn.onclick = startMeeting;
}

function renderMeetingStage() {
  const main = document.getElementById('main-content');
  main.className = 'main-content stage-meeting';
  main.innerHTML = '';

  document.getElementById('page-title').textContent = 'Rendez-vous en cours';

  const grid = document.createElement('div');
  grid.id = 'cards-grid';
  grid.className = 'cards-grid';
  grid.appendChild(buildAstralCard(pageData.astral));
  grid.appendChild(buildMediumCard(pageData.medium));
  grid.appendChild(buildEvalCard());
  grid.appendChild(buildSuggestionsCard());
  main.appendChild(grid);

  const btn = document.getElementById('start-rdv-btn');
  btn.textContent = 'Fin du rendez-vous';
  btn.className = 'btn btn-primary btn-action';
  btn.onclick = showFinishPopup;
}


function buildAstralCard(data) {
  const card = document.createElement('div');
  card.id = 'astral-card';
  card.className = 'card';

  const title = document.createElement('h2');
  title.className = 'card-title';
  title.textContent = 'Profil astral du client';
  card.appendChild(title);

  const inner = document.createElement('div');
  inner.className = 'astral-grid';

  const left = document.createElement('div');
  left.className = 'astral-col';
  left.appendChild(buildAstralRow('Signe du zodiaque', data.zodiac_sign));
  left.appendChild(buildAstralRow('Signe astrologique chinois', data.ch_sign));

  const right = document.createElement('div');
  right.className = 'astral-col';
  right.appendChild(buildColorRow(data.color));
  right.appendChild(buildAstralRow('Votre animal totem', data.animal));

  inner.appendChild(left);
  inner.appendChild(right);
  card.appendChild(inner);
  return card;
}

function buildAstralRow(label, value) {
  const row = document.createElement('div');
  row.className = 'astral-row';

  const lbl = document.createElement('span');
  lbl.className = 'astral-label';
  lbl.textContent = label;

  const img = document.createElement('img');
  img.className = 'astral-img';
  img.src = ''; // TODO: set image path once assets are available
  img.alt = value;

  const val = document.createElement('span');
  val.className = 'astral-value';
  val.textContent = value;

  row.appendChild(lbl);
  row.appendChild(img);
  row.appendChild(val);
  return row;
}

function buildColorRow(color) {
  const row = document.createElement('div');
  row.className = 'astral-row';

  const lbl = document.createElement('span');
  lbl.className = 'astral-label';
  lbl.textContent = 'Couleur porte bonheur';

  const swatch = document.createElement('span');
  swatch.id = 'color-swatch';
  swatch.className = 'color-swatch';
  swatch.style.background = color;

  const val = document.createElement('span');
  val.className = 'astral-value';
  val.textContent = color;

  row.appendChild(lbl);
  row.appendChild(swatch);
  row.appendChild(val);
  return row;
}

function buildHistoryCard(data) {
  const card = document.createElement('div');
  card.id = 'history-card';
  card.className = 'card';

  const title = document.createElement('h2');
  title.className = 'card-title';
  title.textContent = 'Historique du client';
  card.appendChild(title);

  const list = document.createElement('ul');
  list.id = 'history-list';
  list.className = 'history-list';

  if (!data.length) {
    const empty = document.createElement('li');
    empty.textContent = 'Aucun historique.';
    list.appendChild(empty);
  } else {
    data.forEach((rdv) => {
      const li = document.createElement('li');
      li.className = 'history-item';

      const name = document.createElement('span');
      name.className = 'history-name';
      name.textContent = `${rdv.medium_name} - ${rdv.medium_type}`;

      const date = document.createElement('span');
      date.className = 'history-date';
      date.textContent = rdv.date;

      li.appendChild(name);
      li.appendChild(date);
      list.appendChild(li);
    });
  }

  card.appendChild(list);
  return card;
}

function buildMediumCard(data) {
  const card = document.createElement('div');
  card.id = 'medium-card';
  card.className = 'card card-medium';

  const title = document.createElement('h2');
  title.className = 'card-title';
  title.textContent = 'Le medium à incarner';
  card.appendChild(title);

  const inner = document.createElement('div');
  inner.className = 'medium-inner';

  const img = document.createElement('img');
  img.id = 'medium-img';
  img.className = 'medium-img';
  img.src = ''; // TODO: set image path once assets are available
  img.alt = data.name;

  const info = document.createElement('div');
  info.className = 'medium-info';

  const name = document.createElement('h3');
  name.id = 'medium-name';
  name.className = 'medium-name';
  name.textContent = `${data.name} - ${data.type} - ${data.gender}`;

  const desc = document.createElement('p');
  desc.id = 'medium-desc';
  desc.className = 'medium-desc';
  desc.textContent = data.presentation;

  info.appendChild(name);
  info.appendChild(desc);
  inner.appendChild(img);
  inner.appendChild(info);
  card.appendChild(inner);
  return card;
}

function buildEvalCard() {
  const card = document.createElement('div');
  card.id = 'eval-card';
  card.className = 'card';

  const title = document.createElement('h2');
  title.className = 'card-title';
  title.textContent = "Evaluez le client sur l'échelle du bonheur";
  card.appendChild(title);

  const subtitle = document.createElement('p');
  subtitle.className = 'eval-subtitle';
  subtitle.textContent = '(notes de 1 à 4)';
  card.appendChild(subtitle);

  const rows = document.createElement('div');
  rows.className = 'eval-rows';

  const topRow = document.createElement('div');
  topRow.className = 'eval-row-pair';
  topRow.appendChild(buildEvalRow('love', 'Amour'));
  topRow.appendChild(buildEvalRow('health', 'Santé'));
  rows.appendChild(topRow);

  const bottomRow = document.createElement('div');
  bottomRow.className = 'eval-row-pair';
  bottomRow.appendChild(buildEvalRow('work', 'Travail'));
  rows.appendChild(bottomRow);

  card.appendChild(rows);

  const applyBtn = document.createElement('button');
  applyBtn.id = 'apply-eval-btn';
  applyBtn.className = 'btn btn-primary';
  applyBtn.textContent = 'Appliquer choix';
  applyBtn.addEventListener('click', fetchSuggestions);
  card.appendChild(applyBtn);

  return card;
}

function buildEvalRow(field, labelText) {
  const row = document.createElement('div');
  row.className = 'eval-row';

  const label = document.createElement('label');
  label.htmlFor = `eval-${field}`;
  label.className = 'eval-label';
  label.textContent = labelText;

  const input = document.createElement('input');
  input.id = `eval-${field}`;
  input.className = 'eval-input';
  input.type = 'number';
  input.min = '1';
  input.max = '4';

  row.appendChild(label);
  row.appendChild(input);
  return row;
}

function buildSuggestionsCard() {
  const card = document.createElement('div');
  card.id = 'suggestions-card';
  card.className = 'card';

  const title = document.createElement('h2');
  title.className = 'card-title';
  title.textContent = 'Conseils';
  card.appendChild(title);

  const placeholder = document.createElement('p');
  placeholder.id = 'suggestions-placeholder';
  placeholder.className = 'suggestions-placeholder';
  placeholder.textContent = 'Remplissez les 3 champs pour obtenir des conseils.';
  card.appendChild(placeholder);

  const content = document.createElement('div');
  content.id = 'suggestions-content';
  card.appendChild(content);

  return card;
}

async function startMeeting() {
  try {
    const res = await fetch('/predictif-server/ActionServlet?todo=start-current-rdv');
    const data = await res.json();
    if (data.op_success) {
      renderMeetingStage();
    } else {
      console.error('Failed to start meeting:', data);
    }
  } catch (e) {
    console.error('Failed to start meeting', e);
  }
}

async function fetchSuggestions() {
  const love = document.getElementById('eval-love')?.value;
  const health = document.getElementById('eval-health')?.value;
  const work = document.getElementById('eval-work')?.value;

  if (!love || !health || !work) return;

  const placeholder = document.getElementById('suggestions-placeholder');
  const content = document.getElementById('suggestions-content');

  try {
    const url = '/predictif-server/ActionServlet?todo=get-predictions&'
      + new URLSearchParams({ love, health, work });
    const res = await fetch(url);
    const data = await res.json();

    if (!data || !data.operation_allowed || !data.predictions?.length) {
      if (placeholder) placeholder.textContent = "Impossible d'obtenir les conseils.";
      return;
    }

    if (placeholder) placeholder.style.display = 'none';
    content.innerHTML = '';

    for (const prediction of data.predictions) {
      const section = document.createElement('div');
      section.className = 'suggestion-section';

      const sText = document.createElement('p');
      sText.className = 'suggestion-text';
      sText.textContent = prediction;

      section.appendChild(sText);
      content.appendChild(section);
    }
  } catch (e) {
    console.error('Failed to fetch suggestions', e);
  }
}

function showFinishPopup() {
  document.getElementById('finish-popup')?.remove();

  const overlay = document.createElement('div');
  overlay.id = 'finish-popup';
  overlay.className = 'popup-overlay';

  const box = document.createElement('div');
  box.className = 'popup-box';

  const title = document.createElement('h2');
  title.className = 'popup-title';
  title.textContent = 'Saisir un commentaire par rapport au rendez-vous';

  const subtitle = document.createElement('p');
  subtitle.className = 'popup-subtitle';
  subtitle.textContent = '(laisser vide si pas de commentaire)';

  const textarea = document.createElement('textarea');
  textarea.id = 'popup-comment';
  textarea.className = 'popup-textarea';
  textarea.placeholder = "Le rendez-vous s'est très bien passé...";

  const validateBtn = document.createElement('button');
  validateBtn.id = 'popup-validate-btn';
  validateBtn.className = 'btn btn-primary';
  validateBtn.textContent = 'Valider';
  validateBtn.addEventListener('click', () => finishMeeting(textarea.value));

  box.appendChild(title);
  box.appendChild(subtitle);
  box.appendChild(textarea);
  box.appendChild(validateBtn);
  overlay.appendChild(box);
  document.body.appendChild(overlay);
}

async function finishMeeting(comment) {
  try {
    const commentUrl = '/predictif-server/ActionServlet?todo=add-comment-to-rdv&'
      + new URLSearchParams({ employee_comment: comment });
    const commentRes = await fetch(commentUrl);
    const commentData = await commentRes.json();

    if (!commentData.op_success) {
      console.error('Failed to save comment:', commentData);
      return;
    }

    const closeRes = await fetch('/predictif-server/ActionServlet?todo=close-rdv');
    const closeData = await closeRes.json();

    if (!closeData.op_success) {
      console.error('Failed to close RDV:', closeData);
      return;
    }

    document.getElementById('finish-popup')?.remove();
    initPage();
  } catch (e) {
    console.error('Failed to finish meeting', e);
  }
}

window.addEventListener('DOMContentLoaded', initPage);
