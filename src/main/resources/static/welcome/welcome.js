import { checkAuth } from '../utils/auth.js'
import { formatDate } from '../utils/unixdate.js'


async function handleStartGame() {
  const isDifficult = document.getElementById('difficultyToggle').checked

  const response = await fetch(`http://localhost:8080/api/questions?isDifficult=${isDifficult}`)

  if (!response.ok) {
    console.log('something went wrong')
    return
  }

  // json = { success: boolean, message: string, data: question[]
  const json = await response.json()

  // handle fail retrieving questions
  if (!json.success) {
    document.getElementById('message-container').textContent = json.message
    return
  }

  const allQuestions = [...json.data.structureQs, ...json.data.reactionQs]

  // store the questions in session storage
  sessionStorage.setItem('questions', JSON.stringify(allQuestions))
  window.location.href = '../questions/questions.html'
}

function renderScoreboard(topScores) {
  const TBODY = document.getElementById('scoreboardTbody')

  for (let i = 0; i < topScores.length; i++) {
    const { username, topScore, attemptDate } = topScores[i]

    const trow = document.createElement('tr')

    const rowTemplate = `
      <td>${username}</td>
      <td>${topScore * 10}</td>
      <td>${formatDate(attemptDate)}</td>
      `

    trow.innerHTML = rowTemplate
    TBODY.appendChild(trow)
  }
}

function renderScores(userScores) {
  const TBODY = document.getElementById('tbody')

  for (let i = 0; i < userScores.length; i++) {
    const { scoreValue, attemptDate } = userScores[i]

    const trow = document.createElement('tr')

    const rowTemplate = `
      <td>${formatDate(attemptDate)}</td>
      <td>${scoreValue * 10}</td>
    `

    trow.innerHTML = rowTemplate
    TBODY.appendChild(trow)
  }
}

function handleLogOut() {
  sessionStorage.clear()
  window.location.href = '../index.html'
}

async function getScoreData(userId) {

  const response = await fetch(`http://localhost:8080/api/welcome?userId=${userId}`)

  if (!response.ok) {
    console.log("Something went wrong with getScoreData GET...")
    return
  }

  /* { success: boolean, message: String, data: { 
        attemptCount: int, 
        leaderboardDTOs: [
          { userId: int, username: String, attemptDate: int (unixtime), topScore: int }, ... ,
        ],
        userScores: [
          { scoreValue: int, attemptDate: int (unixtime) }, ... ,
        ]
        }
      }
   */

  const json = await response.json()

  console.log(json)

  return json.data

}

async function init() {
  // prevent unauthorized users from entering admin area
  checkAuth()

  const userInfo = JSON.parse(sessionStorage.getItem('userInfo'))

  const { attemptCount, leaderboardDTOs, userScores } = await getScoreData(userInfo.userId)

  // const highestScores = JSON.parse(sessionStorage.getItem('highestScores'))
  // const leaderBoard = JSON.parse(sessionStorage.getItem('leaderBoard'))
  // const attemptCount = sessionStorage.getItem('attemptCount')

  document.getElementById('welcome-message').textContent = `Welcome, ${userInfo.username}`

  renderScores(userScores)

  renderScoreboard(leaderboardDTOs)

  document.getElementById(
    'total-attempts'
  ).innerHTML = `Total number of attempts: <span id="total-attempts-num">${attemptCount}</span>`

  document.getElementById('startBtn').addEventListener('click', handleStartGame)
  document.getElementById('logoutBtn').addEventListener('click', handleLogOut)
}

onload = init
