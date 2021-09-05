const fs = require("fs")
const path = require("path")

const targetFile = path.resolve("./.railway/load_completed.txt")

describe("Station #2", () => {
  test("load_completed.txtが存在する", () => {
    expect(fs.existsSync(targetFile)).toBe(true)
  })
  test("load_completed.txtの内容が自動生成されたものから変わっていない", () => {
    const expected = "Hello Railway Android!\nPlease commit this file as is."
    const actual = fs.readFileSync(targetFile, "utf8")
    expect(actual).toBe(expected)
  })
})
