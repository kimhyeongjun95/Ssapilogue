

const MultipleQuestions = ({styles, cross, deleteSurvey, choiceHandleInput, idx, input}) => {
  return (
    <div>
      <input
        placeholder = "객관식 답변" 
        name="surveyOptions"
        value={input.surveyOptions}
        onChange={e => choiceHandleInput(e, idx)}
      />
      <img className={styles} src={cross} alt="cross" onClick={deleteSurvey} />
    </div>
  )
}

export default MultipleQuestions;