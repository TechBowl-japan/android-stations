const childProcess = require("child_process")
const fs = require("fs")
const path = require("path")

const isWindows = process.platform === "win32"

function runUnitTest(railwayId) {
  return new Promise((resolve, reject) => {
    const gradlewProcess = childProcess.spawn(
      isWindows ? "gradlew" : "./gradlew",
      [
        ":app:testDebugUnitTest",
        "--tests com.example.techtrain.railway.S" + railwayId,
        "--rerun-tasks",
        "--console=plain",
        "--no-daemon"
      ],
      {
        "shell": true
      }
    )
    gradlewProcess.stdout.on("data", function(data) {
      // Note: `data` might contain line break characters, so use `write` to reproduce the original output.
      process.stdout.write(data.toString())
    })
    gradlewProcess.on("close", function(code) {
      return resolve()
    })
    gradlewProcess.on("exit", function(code) {
      return resolve()
    })
    gradlewProcess.on("error", function(err) {
      return reject()
    })
  })
}

async function run() {
  const railwayId = parseInt(process.argv[2]).toString()
  try {
    await runUnitTest(railwayId)
  } catch (err) {
    console.log("Error on runUnitTest" + err.message)
  }

  const resultXml = path.resolve("./app/build/test-results/testDebugUnitTest/TEST-com.example.techtrain.railway.S" + railwayId + ".xml")
  if (!fs.existsSync(resultXml)) {
    fs.mkdirSync(path.resolve("./app/build/test-results/testDebugUnitTest/"), { "recursive" : true })
    const compileErrorResultXml = path.resolve("./.techtrain/utils/compile-error.xml")
    fs.copyFileSync(compileErrorResultXml, resultXml)
  }
}

run()
