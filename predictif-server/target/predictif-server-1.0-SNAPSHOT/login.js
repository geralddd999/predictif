let selectedRole = 'client';

function initLogin() {
    // tab switching - the client/employe thing
    document.querySelectorAll('.tab').forEach(tab => {
        tab.addEventListener('click', () => {
            document.querySelectorAll('.tab').forEach(t => {
                t.classList.remove('active');
                t.setAttribute('aria-selected', 'false');
            });
            tab.classList.add('active');
            tab.setAttribute('aria-selected', 'true');
            selectedRole = tab.dataset.role;
            clearFeedback();
        });
    });

    // password visibility toggle
    document.getElementById('toggle_pw').addEventListener('click', () => {
        const input = document.getElementById('passwd_input');
        const isHidden = input.type === 'password';
        input.type = isHidden ? 'text' : 'password';
        document.getElementById('icon_show').style.display = isHidden ? 'none' : '';
        document.getElementById('icon_hide').style.display = isHidden ? ''     : 'none';
    });

    // submit click
    document.getElementById('submit_btn').addEventListener('click', handleSubmit);


    document.addEventListener('keydown', e => {
        if (e.key !== 'Enter') return;
        const tag = document.activeElement?.tagName;
        if (tag === 'INPUT') document.getElementById('submit_btn').click();
    });
}

function showFeedback(msg, type) {
    const f = document.getElementById('feedback');
    f.className = `feedback ${type}`;
    f.textContent = msg;
}

function clearFeedback() {
    const f = document.getElementById('feedback');
    f.className = 'feedback';
    f.textContent = '';
}

async function handleSubmit() {
    clearFeedback();

    const email = document.getElementById('email_input').value.trim();
    const passwd = document.getElementById('passwd_input').value;
    const btn = document.getElementById('submit_btn');

    if (!email || !passwd) {
        showFeedback('Veuillez remplir tous les champs.', 'error');
        return;
    }

    btn.classList.add('loading');
    btn.disabled = true;

    try {
        let params = {
            login_mail: email,
                password: passwd
        };
        
        let url = '/predictif-server/ActionServlet?todo=authenticate-' + selectedRole + '&'+new URLSearchParams(params);
        console.log(url);
        const response = await fetch(url);
        /*{
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({
                
            })
        }
        */
        const data = await response.json();
        
        console.log(data);

        if (data.auth_success) {
            showFeedback('Connexion réussie ! Redirection...', 'success');
            if (data.redirect) {
                setTimeout(() => { window.location.href = data.redirect; }, 1000);
            }
        } else {
            showFeedback(data.message || 'Identifiants incorrects.', 'error');
        }
    } catch (e) {
        showFeedback('Impossible de contacter le serveur.', 'error');
        console.error(e);
    } finally {
        btn.classList.remove('loading');
        btn.disabled = false;
    }
}

window.addEventListener('DOMContentLoaded', initLogin);