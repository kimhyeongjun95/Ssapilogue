import * as React from 'react';
import Box from '@mui/material/Box';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function BasicSelect({ option, onChange }) {

  return (
    <Box >
      <FormControl sx={{ minWidth: 120 }}>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={option}
          onChange={onChange}
          style={{ fontFamily:'GmarketSansMedium'}}
          defaultValue="전체"
        >
          <MenuItem style={{ fontFamily:'GmarketSansMedium'}} value={"전체"}>전체</MenuItem>
          <MenuItem style={{ fontFamily:'GmarketSansMedium'}} value={"공통"}>공통</MenuItem>
          <MenuItem style={{ fontFamily:'GmarketSansMedium'}} value={"특화"}>특화</MenuItem>
          <MenuItem style={{ fontFamily:'GmarketSansMedium'}} value={"자율"}>자율</MenuItem>
        </Select>
      </FormControl>
    </Box>
  );
}