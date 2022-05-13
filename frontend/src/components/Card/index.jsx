import "./style.scss"
import { Chip } from "@mui/material"
import Thumbsup from "../../assets/thumbsup.png"
import View from "../../assets/view.png"
import Comment from "../../assets/comment.png"

const Card = ({ title, content, category, likeCnt, viewCnt, commentCnt, techStack, thumbnail }) => {
  return (
    <div className="card">
      <div className="image-box">
        <img className="card-thumbnail" src={thumbnail} alt="thumbnail" />
      </div>
      <div className="text-box">
        <div className="card-title-content">
          <p className="card-title">{title}</p>
          <p className="card-content">{content}</p>
        </div>
        <div className="card-stack">
          {techStack.map((stack, idx) => (
            <span key={idx}>
              <Chip 
                style={{ margin : "3%", backgroundColor : "#3396F4", color:'white', fontWeight:'bold'}}
                label={stack} 
              />
            </span>
          ))}
        </div>
        <div className="card-bottom">
          <Chip
            style={{ fontWeight: 'bold' }}
            label={category}
            color="primary" 
            variant="outlined"
          />
          <div className="bottom-image-box">
            <img className="bottom-image" src={Thumbsup} alt="thumsup" />
            <span className="count-number">{likeCnt}</span>
          </div>
          <div className="bottom-image-box">
            <img className="bottom-view-image" src={View} alt="views" />
            <span className="count-number">{viewCnt}</span>
          </div>
          <div className="bottom-image-box">
            <img className="bottom-image" src={Comment} alt="comments" />
            <span className="count-number">{commentCnt}</span>
          </div>
        </div>
      </div>
    </div>
  )
}
export default Card;