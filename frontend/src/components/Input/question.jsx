import TextField from "@mui/material/TextField";
import "./style.scss"

function questionType( { InputTitle,inputValue ,inputSetValue, pilsu,inputId, placeholder }){
  const handleChange = (event) => {
    inputSetValue(event.target.value);
  };
  if (pilsu){
    return <div className="main-div">
      <div className="input-div">
        <p className="p-style"> {InputTitle} </p>
        <TextField
          style={{width:"100%", fontFamily:"GmarketSansMedium"}}
          size = "small"
          value={inputValue}
          className={inputId}
          onChange={handleChange}
          required
          id="outlined-basic"
          variant="outlined"
          placeholder={placeholder}
        />
      </div>
    </div>
  }else{
    return <div className="main-div">
      <div className="input-div">
        <p className="p-style"> {InputTitle} </p>
        <TextField
          style={{width:"100%", fontFamily:"GmarketSansMedium"}}
          size = "small"
          value={inputValue}
          className={inputId}
          onChange={handleChange}
          id="outlined-basic"
          variant="outlined"
        />
      </div>
    </div>

  }
}

export default questionType