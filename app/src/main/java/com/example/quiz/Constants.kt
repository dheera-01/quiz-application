package com.example.quiz

object Constants {

    const val USER_NAME: String  = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestion(): ArrayList<Question>{
        val questionList = ArrayList<Question>()

        val ques1 = Question(1,
            "Which of the following has worst time complexity?",
            "Heap Sort",
            "MergeSort",
            "Radix Sort",
            "Insertion Sort",
            4)
        questionList.add(ques1)

        val ques2 = Question(2,
            "Does Merge Sort uses auxiliary space?",
            "Yes",
            "No",
            "Maybe",
            "None of the above",
            1)
        questionList.add(ques2)

        return questionList
    }
}