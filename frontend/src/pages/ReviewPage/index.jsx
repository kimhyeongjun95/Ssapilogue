import React, { useEffect, useState } from "react";
import API from "../../api/API";
import { useParams } from "react-router-dom";
import {
  BarChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Bar
} from 'recharts';
import { Box, Tabs, Tab, Typography } from '@mui/material';
import PropTypes from 'prop-types';
import "./style.scss"

function TabPanel(props) {
  const { children, value, index, ...other } = props;
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography component={"span"} >{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const ReviewPage = () => {
  const id = useParams().projectId;
  const [reviews, setReviews] = useState([]);

  const getReview = async (id) => {
    const response = await API.get(`/api/review/${id}`);
    setReviews(response.data.reviewList)
  }


  useEffect(() => {
    getReview(id);
  }, [id])

  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className="review-detail">
      <h1>리뷰 페이지!</h1>

      <Box sx={{ width: '80%' }}>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
            {reviews.map((review, idx) => (
              <Tab key={idx} label={idx+1} {...a11yProps(idx+1)} />
            ))}
          </Tabs>
        </Box>
        {reviews.map((review, idx) => (
          <div key={idx}>
            <TabPanel value={value} index={idx}>
              {review.surveyType === "주관식" ?
                <>
                  <h1>{review.surveyTitle}</h1>
                  {/* <h1>{review.subjectiveReviews[idx]["content"]}</h1> */}
                </>
                :
                <>
                  <h1>{review.surveyTitle}</h1>
                  <BarChart width={730} height={250} data={review.objectiveReviews} >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="optionContent" />
                    <YAxis />
                    <Tooltip />
                    <Bar dataKey="count" fill="#3949AB" />
                  </BarChart>
                </>
              }
            </TabPanel>
          </div>
        ))}
      </Box>
    </div>
  )
}

export default ReviewPage;