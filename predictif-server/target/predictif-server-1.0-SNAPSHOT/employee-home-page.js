async function initPage(){
  try{
    const res = await fetch('/predictif-server/ActionServlet?todo=consult-employee-rdv');
    const data = await res.json();
    console.log(data);
    if(data.error){
      console.warn('Access denied:', data.error);
      return;
    }

    const astral_profile = data.client_astral_profile;
    const client_history = data.client_history;
    const medium = data.medium;

    const main_container = document.getElementById("main-content");

    if(!astral_profile || !client_history || !medium){
      console.warn('No active RDV found');
      
      main_container.innerHTML = '';
      const feedback_container = document.createElement("div");
      const msg = document.createElement('p');
      msg.innerText = "No active RDV found";
      feedback_container.appendChild(msg);
      return;
    }

    const main_title = document.createElement('h1');
    main_title.innerText = "Votre rendez-vous en cours";
    main_container.appendChild(main_title);
    
    populateClientHistory(client_history);
    populateMedium(medium);
    populateAstralProfile(astral_profile);

    let ready_btn = document.getElementById('start-rdv-btn');
    ready_btn.addEventListener('click', startMeeting);
  }catch(e){
    console.error('Failed to get rdv-information', e);
  }
}

function populateAstralProfile(data){
  const container = document.getElementById("astral-profile-container");

  const title = document.createElement("h3");
  title.innerText = "Profil astral du client";
  container.appendChild(title);

  const zodiac_sign_container = document.createElement("div");
  const zodiac_sign_label = document.createElement("span");
  zodiac_sign_label.innerText = "Signe du zodiaque : ";
  const zodiac_sign_txt = document.createElement("h2");
  zodiac_sign_txt.innerText = data.zodiac_sign;
  const zodiac_sign_img = document.createElement('img');
  zodiac_sign_img.src = ''; // TODO: set path to zodiac sign image
  zodiac_sign_img.alt = data.zodiac_sign;
  zodiac_sign_container.appendChild(zodiac_sign_label);
  zodiac_sign_container.appendChild(zodiac_sign_txt);
  zodiac_sign_container.appendChild(zodiac_sign_img);

  const color_container = document.createElement('div');
  const color_label = document.createElement("span");
  color_label.innerText = "Couleur porte bonheur : ";
  const color_txt = document.createElement("h2");
  color_txt.innerText = data.color;
  color_container.appendChild(color_label);
  color_container.appendChild(color_txt);

  const ch_sign_container = document.createElement('div');
  const ch_sign_label = document.createElement("span");
  ch_sign_label.innerText = "Signe astrologique chinois : ";
  const ch_sign_txt = document.createElement('h2');
  ch_sign_txt.innerText = data.ch_sign;
  const ch_sign_img = document.createElement('img');
  ch_sign_img.src = ''; // TODO: set path to chinese sign image
  ch_sign_img.alt = data.ch_sign;
  ch_sign_container.appendChild(ch_sign_label);
  ch_sign_container.appendChild(ch_sign_txt);
  ch_sign_container.appendChild(ch_sign_img);

  const animal_container = document.createElement('div');
  const animal_label = document.createElement("span");
  animal_label.innerText = "Animal totem : ";
  const animal_txt = document.createElement("h2");
  animal_txt.innerText = data.animal;
  const animal_img = document.createElement('img');
  animal_img.src = ''; // TODO: set path to animal totem image
  animal_img.alt = data.animal;
  animal_container.appendChild(animal_label);
  animal_container.appendChild(animal_txt);
  animal_container.appendChild(animal_img);

  container.appendChild(zodiac_sign_container);
  container.appendChild(color_container);
  container.appendChild(ch_sign_container);
  container.appendChild(animal_container);
}

function populateClientHistory(data){
  const container = document.getElementById("client-history-container");
  const container_title = document.createElement("h2");
  container_title.innerText = "Historique du client";
  container.appendChild(container_title);

  const rdv_list = document.createElement('ul');
  data.forEach((rdv) => {
    const entry = document.createElement('li');

    const med = document.createElement('span');
    med.innerText = rdv.medium_name + ' - ' + rdv.medium_type;

    const date = document.createElement('span');
    date.innerText = rdv.date;

    entry.appendChild(med);
    entry.appendChild(date);
    rdv_list.appendChild(entry);
  });
  container.appendChild(rdv_list);
}

function populateMedium(data){
  const container = document.getElementById("medium-container");

  const container_title = document.createElement("h2");
  container_title.innerText = "Le medium a incarner";
  container.appendChild(container_title);

  const medium_img = document.createElement('img');
  medium_img.src = ''; // TODO: set path to medium image
  medium_img.alt = data.name;
  container.appendChild(medium_img);

  const medium_title = document.createElement('h2');
  medium_title.innerText = data.name + ' - ' + data.type + ' - ' + data.gender;

  const medium_description = document.createElement('p');
  medium_description.innerText = data.presentation;

  container.appendChild(medium_title);
  container.appendChild(medium_description);
}

async function startMeeting(){
  try{
    const res = await fetch('/predictif-server/ActionServlet?todo=start-current-rdv');
    const data = await res.json();

    if(data.op_success){
      //execute function to clear the page and show other things
    }else{
      //raise error feedback element or something like that
    }
  }catch(e){
    console.error('Failed to start meeting', e);
  }
}


window.addEventListener('DOMContentLoaded', initPage);
