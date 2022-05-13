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
import moment from 'moment';
import "./style.scss"
import { jsPDF } from "jspdf";
import html2canvas from "html2canvas";

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
        <Box>
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
    console.log(response);
    setReviews(response.data.reviewList)
  }

  useEffect(() => {
    getReview(id);
  }, [id])

  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const printDocument = () => {
    html2canvas(document.getElementById("printReview"), {
      width: window.width,
      height: 877,
    }).then(function(canvas) {
      console.log(document.getElementById("printReview"))
      var imgData = canvas.toDataURL('image/png');
      var imgWidth = 210;
      var pageHeight = imgWidth * 1.414;
      var imgHeight = canvas.height * imgWidth / canvas.width;

      var doc = new jsPDF({
        'orientation': 'p',
        'unit': 'mm',
        'format': 'a4'
      });

      doc.addImage(imgData, 'PNG', 0,0 , imgWidth, imgHeight);
      doc.save('sample_A4.pdf');
      console.log('Reached here?');
    });
  } 


  return (
    <div className="review-detail">
      <div>
        <button onClick={printDocument}>Print</button>
      </div>
      {/* <Box sx={{ width: '80%' }}> */}
      <Box className="review_box">
        <Box>
          <Tabs 
            TabIndicatorProps={{ style: { backgroundColor: "#C3E2F9" } }} 
            value={value} 
            onChange={handleChange} 
            indicatorColor="secondary"
            aria-label="basic tabs example"
          >
            {reviews.map((review, idx) => (
              <Tab key={idx} label={idx+1} {...a11yProps(idx+1)} />
            ))}
          </Tabs>
        </Box>
        {reviews.map((review, idx) => (
          <div id="printReview" className="review_title_box" key={idx}>
            <TabPanel value={value} index={idx}>
              {review.surveyType === "주관식" ?
                <>
                  <div className="review_title_count">
                    <h4 className="review_title">{review.surveyTitle}</h4>
                    <h4 className="review_title">TOTAL: {review.totalCount}명</h4>
                  </div>
                  <div className="review_menu">
                    <p className="review_content_title">내용</p>
                    <p className="review_date_title">날짜</p>
                    <p className="review_writer_title">작성자</p>
                  </div>
                  {review.subjectiveReviews.map((res, idx) => (
                    <div className="review_contents" key={idx}>
                      <p className="review_content">{res.content}</p>
                      <p className="review_date_title">{moment(res.createAt).format('YYYY년 MM월 DD일')}</p>
                      <p className="review_writer_title">{res.nickname}</p>
                    </div>
                  ))}
                </>

                :

                <>
                  <div className="review_title_count_options">
                    <h4 className="review_title">{review.surveyTitle}</h4>
                    <h4 className="review_title">TOTAL: {review.totalCount}명</h4>
                  </div>
                  <div className="review_chart_box">
                    <BarChart width={750} height={250} data={review.objectiveReviews} >
                      <CartesianGrid strokeDasharray="3 3" />
                      <XAxis dataKey="optionContent" />
                      <YAxis />
                      <Tooltip />
                      <Bar dataKey="count" fill="#3949AB" />
                    </BarChart>
                  </div>
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