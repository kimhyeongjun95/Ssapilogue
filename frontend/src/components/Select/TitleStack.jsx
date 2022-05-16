import * as React from 'react';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function BasicSelect({ option, onChange }) {

  return (
    <Box >
      <FormControl sx={{ minWidth: 120 }}>
        <InputLabel id="demo-simple-select-label">옵션</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={option}
          label="제목"
          onChange={onChange}
        >
          <MenuItem value={"제목"}>제목</MenuItem>
          <MenuItem value={"기술스택"}>기술스택</MenuItem>
        </Select>
      </FormControl>
    </Box>
  );
}