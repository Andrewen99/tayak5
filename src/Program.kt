import instruments.MyException
import statements.StatementHelper

class Program(codePath: String) {
    val lines = FileReader.parseFile(codePath)
    var vars = hashMapOf<Char, Int>()
    var brackets = mutableListOf<Char>()
    var ifBrackets = mutableListOf<Char>()
    var ifFlag = false

    var currentLine = 0

    fun isFinished(): Boolean {
        return lines.lastIndex < currentLine
    }

    fun innerIfProgram(flag: Boolean) {
        if (lines[currentLine].contains("{")) {
            ifBrackets.add('{')
            currentLine++
        }
        else {
            currentLine++
            if (lines[currentLine].contains("{")) {
                ifBrackets.add('{')
                currentLine++
            } else {
                throw MyException(this, "Где '{' а?")
            }
        }

        while (ifBrackets.size > 0 && !isFinished()) {
            findBrackets()
            if (ifBrackets.size > 0 && !isFinished()) {
                if (flag) {
                    StatementHelper.defineAndExecStatement(this)
                } else {
                    currentLine++
                }
            }
        }
    }

    fun innerForProgram() {
        if (lines[currentLine].contains("{")) {
            brackets.add('{')
            currentLine++
        }

        while (brackets.size > 0 && !isFinished()) {
            findForBrackets()
            if (brackets.size > 0 && !isFinished()) {
                StatementHelper.defineAndExecStatement(this)
            }
        }
    }

    fun findBrackets() {
        if (lines[currentLine].contains("{")) {
            ifBrackets.add('{')
            currentLine++
        }
        if (lines[currentLine].contains("}")) {
            ifBrackets.removeAt(ifBrackets.lastIndex)
            currentLine++
        }
    }

    fun findForBrackets() {
        if (!lines[currentLine].trim().startsWith("if")) {
            if (lines[currentLine].contains("{")) {
                brackets.add('{')
                currentLine++
            }
            if (lines[currentLine].contains("}")) {
                brackets.removeAt(brackets.lastIndex)
            }
        }
    }


}