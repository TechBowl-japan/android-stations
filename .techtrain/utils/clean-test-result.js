const fs = require("fs")
const path = require("path")

const targetDirectory = path.resolve("./app/build/test-results")

fs.rmSync(
  targetDirectory,
  {
    recursive:true,
    force:true
  }
)
