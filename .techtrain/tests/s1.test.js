const fs = require("fs")
const path = require("path")

const targetFile = path.resolve("installed.png")

describe("Station #1", () => {
  test("installed.pngが存在する", () => {
    expect(fs.existsSync(targetFile)).toBe(true)
  })
})
