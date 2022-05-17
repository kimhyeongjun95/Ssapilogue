import "./style.scss"
import { Chip } from "@mui/material"
import Thumbsup from "../../assets/thumbsup.png"
import View from "../../assets/view.svg"
import Comment from "../../assets/comment.png"
import BookMarked from '../../assets/BookMarked.png'
import NotBookMarked from '../../assets/NotBookMarked.png'
import Default from '../../assets/SSAFY.png'

const Card = ({ title, content, category, likeCnt, viewCnt, commentCnt, techStack, thumbnail, bookmark }) => {
  return (
    <div className="small-card">
      <div className="image-box">
        <img 
          className="card-bookmark"
          src={bookmark === true ? BookMarked : NotBookMarked}
          alt="bookmark"
        />
        { (thumbnail) ?
          <img className="card-thumbnail" src={thumbnail} alt="thumbnail" />
          : <img className="card-thumbnail" src={Default} alt="thumbnail" />
        }     
      </div>
      <div className="text-box">
        <div className="card-title-content">
          <p className="card-title">{title}</p>
          <p className="card-content">{content}</p>
        </div>
        <div className="small-card-stack">
          {techStack.map((stack, idx) => (
            <span className="card-stack-span" key={idx}>
              <Chip 
                style={{ height : "24px", backgroundColor : "#3396F4", color:'white', fontWeight:'bold'}}
                label={stack} 
              />
            </span>
          ))}
        </div>
        <div className="card-bottom">
          <Chip
            style={{ fontWeight: 'bold', height: "20px",marginBottom : "40px"}}
            label={category}
            color="primary" 
            variant="outlined"
          />
          <div className="small-card-bottom-right">
            <div className="small-bottom-image-box">
              <img className="small-bottom-image" src={Thumbsup} alt="thumsup" />
              <span className="count-number">{likeCnt}</span>
            </div>
            <div className="small-bottom-image-box">
              <img className="small-bottom-view-image" src={View} alt="views" />
              <span className="count-number">{viewCnt}</span>
            </div>
            <div className="small-bottom-image-box">
              <img className="small-bottom-image" src={Comment} alt="comments" />
              <span className="count-number">{commentCnt}</span>
            </div>
          </div>
          
        </div>
      </div>
    </div>
  )
}
export default Card;