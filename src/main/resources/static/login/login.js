function resetErrorFields() {
  document.getElementById(`invalid-username`).textContent = ''
  document.getElementById(`invalid-password`).textContent = ''
  document.getElementById('invalid-submit').textContent = ''
}

function checkValidity() {
  let result = true

  const username = document.getElementById('username').value
  const password = document.getElementById('password').value

  let errObj = { username: '', password: '' }

  if (!username) {
    result = false
    errObj.username = 'Please enter a username.'
  }

  if (!password) {
    result = false
    errObj.password = 'Please enter a password.'
  }

  // if (username && !username.match(/^[a-zA-Z0-9]+@(student\.)?instatute\.edu\.au$/)) {
  //   result = false
  //   errObj.username = 'Invalid username.'
  // }

  if (password && !password.match(/^.{8,}$/)) {
    result = false
    errObj.password = 'A password must have at least 8 characters.'
  }

  // proceed if no errors
  if (result) return true

  // otherwise, print errors to screen
  for (const [key, value] of Object.entries(errObj)) {
    // ignore the keys which don't have values (ie those which didn't have errors)
    if (!value) continue

    const errorMsgLocation = document.getElementById(`invalid-${key}`)
    errorMsgLocation.textContent = `Error: ${value}`
  }
}

function show() {
  var x = document.getElementById('password')

  if (x.type === 'password') {
    x.type = 'text'
  } else {
    x.type = 'password'
  }
}

function init() {
  sessionStorage.clear()
  document.getElementById('loginForm').addEventListener('submit', handleSubmit)
  document.getElementById('flexCheckIndeterminate').addEventListener('change', show)
  document.getElementById('flexCheckIndeterminate').checked = false
}

async function handleSubmit(event) {
  event.preventDefault()
  resetErrorFields()

  // only post the data if the user input is valid
  if (!checkValidity()) return

  console.log('passed client-side validation')

  const formData = new FormData(event.target)

  // index.html is in the root folder, so url has to path from there (?)
  const response = await fetch('http://localhost:8080/api/login', {
    method: 'POST',
    body: formData,
  })

  if (!response.ok) {
    console.log('something went wrong')
    return
  }



  // { success: boolean, message: String, data: { userId: int, username: String, isAdmin: boolean }}
  const json = await response.json()

  console.log(json)

  // handle failure to log in
  if (!json.success) {
    document.getElementById('invalid-submit').textContent = json.message
    return
  }

  sessionStorage.setItem('userInfo', JSON.stringify(json.data))

  // if admin, go to admin page
  if (json.data.isAdmin) {
    window.location.href = './admin/admin.html'
    // return isn't necessary but perhaps good to have just in case
    return
  }
  /*
  json.data = {
    attemptCount: int,
    leaderboardDTOs: [{
      userId: int,
      username: String,
      attemptDate: Date
    }, ...],
    userDTO: {
      userId: int,
      username: String,
      isAdmin: false,
    },
    userScores: [{
      scoreValue: int,
      attemptDate: Date
    }, ...]

*/

  // sessionStorage.setItem('leaderBoard', JSON.stringify(json.data.leaderboardDTOs))
  // sessionStorage.setItem('attemptCount', json.data.attemptCount)
  // sessionStorage.setItem('highestScores', JSON.stringify(json.data.userScores))

  window.location.href = './welcome/welcome.html'
}

onload = init
