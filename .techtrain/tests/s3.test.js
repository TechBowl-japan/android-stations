const fs = require("fs")
const path = require("path")

const targetFile = path.resolve("./.railway/build_and_run_completed.txt")

describe("Station #2", () => {
  test("build_and_run_completed.txtが存在する", () => {
    expect(fs.existsSync(targetFile)).toBe(true)
  })
  test("build_and_run_completed.txtの内容が自動生成されたものから変わっていない", () => {
    const expected = "Your first app is installed to the device!\nPlease commit this file as is."
    const actual = fs.readFileSync(targetFile, "utf8")
    expect(actual).toBe(expected)
  })
})
