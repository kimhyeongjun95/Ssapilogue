import TextField from "@mui/material/TextField";
import "./style.scss"
import { useFormControl } from '@mui/material/FormControl';

function questionType( { InputTitle,inputValue ,inputSetValue, pilsu }){
  const handleChange = (event) => {
    inputSetValue(event.target.value);
  };
  if (pilsu){
    return <div className="main-div">
      <div className="input-div">
        <p className="p-style"> {InputTitle} </p>
        <input
          className="question-style"
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
    return <div className="main-div">
      <div className="input-div">
        <p className="p-style"> {InputTitle} </p>
        <input
          className="question-style"
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