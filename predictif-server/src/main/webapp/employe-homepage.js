async function initPage(){
  try{
    const res = await fetch('/predictif-server/ActionServlet?todo=consult-employee-rdv');
    const data = await res.json();

    const astral_profile = data.astral_profile; // obj
    const client_history = data.client_history; //array
    const medium = data.medium;

    populateClientHistory(client_history);
    populateMedium(medium);
    populateAstralProfile(astral_profile);

    let ready_btn = document.getElementById('start-rdv-btn');
    ready_btn.addEventListener('click', startMeeting);
  }catch(e){
    console.log('Failed to get rdv-information',e);
  }
}

function populateAstralProfile(data){
  const container = document.getElementById("astral-profile-container");
  
  let zodiac_sign_container = document.createElement("div");
  
  let zodiac_sign_img = fetch('/'); // TODO : get the path for getting the images from the server;
  let zodiac_sign = data.zodiac_sign;
  zodiac_sign_txt = (document.createElement("h2").innerText = zodiac_sign);
  zodiac_sign_container.innerText = "Profil astral du client";
  zodiac_sign_container.append(zodiac_sign_img);
  zodiac_sign_container.append(zodiac_sign_txt);

  let color_container = document.createElement('div');
  color_container.innerText = "Couleur porte bonheur";
  let color = data.color;
  color_txt = (document.createElement("h2").innerText = color);
  color_container.appendChild(color_txt);

  let ch_sign_container = document.createElement('div');
  ch_sign_container.innerText = "Signe astrologique chinois";
  let ch_sign = data.ch_sign;
  let ch_sign_txt = (document.createElement('h2').innerText = ch_sign);
  let ch_sign_img = fetch('/'); //same todo as b4
  ch_sign_container.appendChild(ch_sign_txt);
  ch_sign_container.appendChild(ch_sign_img);

  let animal_container = document.createElement('div');
  animal_container.innerText = "Votre animal totem"; 
  let animal = data.animal;
  let animal_txt = (document.createElement("h2").innerText = animal);
  let animal_img = fetch('/'); // TODO : get the path
  //turn animal_img to a div or a img and then insert the fetched img to it
  animal_container.appendChild(animal_txt);
  animal_container.appendChild(animal_img);

  

  container.appendChild(zodiac_sign_container);
  container.appendChild(color_container);
  container.appendChild(ch_sign_container);
  container.appendChild(animal_container);

}

function populateClientHistory(data){
  //data is an array
  const container = document.getElementById("client-history-container");
  const container_title = document.createElement("h2");
  container_title.innerText = "Historique du client";

  const rdv_list = document.createElement('ul');
  data.forEach((rdv)=>{
    const entry = document.createElement('li');
    const med = document.createElement('span').innerText = rdv.medium_name + '-' + rdv.medium_type;
    const date = document.createElement('span').innerText = rdv.date;

    entry.appendChild(med);
    entry.appendChild(date);

    rdv_list.appendChild(entry);
  });


}

function populateMedium(data){
  const container = document.getElementById("medium-container");
  let container_title = document.createElement("h2");
  container_title.innerText = "Le medium a incarner";

  container.appendChild(container_title);

  let medium_img = fetch('/'); //todo!!
  let medium_title = data.name + '-' +data.type + '-' + data.gender;
  medium_title = document.createElement('h2').innerText = medium_title;

  let medium_description = document.createElement('p').innerText = data.presentation;

  container.appendChild(medium_img);
  container.appendChild(medium_title);
}

function startMeeting(){
  //associated to the Pret(e) button, to query the server to see if its able to go the next step
  const res = await fetch('/predictif-server/ActionServlet?todo=start-current-rdv');
  const data = await res.json();

  if (data.op_success){
    //execute function to clear the page and show other thigns
  }else{
    //raise error feedback element or something like that
  }
}



window.addEventListener('DOMContentLoaded', initPage);