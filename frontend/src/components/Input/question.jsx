import react from "react"
import TextField from "@mui/material/TextField";

function questionType( { InputTitle,inputValue ,inputSetValue, pilsu }){
  const handleChange = (event) => {
    inputSetValue(event.target.value);
  };
  if (pilsu){
    return <div style={{width: "40%"}}>
      <div>
        <p style={{marginBottom : "1%"}}> {InputTitle} </p>
        <TextField
          style={{width:"100%"}}
          size = "small"
          value={inputValue}
          onChange={handleChange}
          required
          id="outlined-basic"
          variant="outlined"
        />
      </div>
    </div>
  }else{
    return <div style={{width: "40%"}}>
      <div>
        <p style={{marginBottom : "1%"}}> {InputTitle} </p>
        <TextField
          style={{width:"100%"}}
          size = "small"
          value={inputValue}
          onChange={handleChange}
          id="outlined-basic"
          variant="outlined"
        />
      </div>
    </div>

  }
}

export default questionType