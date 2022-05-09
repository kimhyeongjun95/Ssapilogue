import React from "react";
import { Outlet } from 'react-router-dom';

const OpinionPage = () => {
  return (
    <>
      <h1>Review | Bug Report</h1>
		 <Outlet />
    </>
  )
}

export default OpinionPage;