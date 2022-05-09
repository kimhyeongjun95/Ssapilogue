import React, { useEffect, useState } from "react";
import API from "../../api/API";
import { useParams, Link } from "react-router-dom";
import {
  BarChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Bar
} from 'recharts';

const ReviewPage = () => {
  const id = useParams().projectId;
  const [reviews, setReviews] = useState([]);

  // const data = [
  //   {
  //     "name": "Page A",
  //     "pv": 2400
  //   },
  //   {
  //     "name": "Page B",
  //     "pv": 1398
  //   },
  //   {
  //     "name": "Page C",
  //     "pv": 9800
  //   },
  //   {
  //     "name": "Page D",
  //     "pv": 3908
  //   },
  //   {
  //     "name": "Page E",
  //     "pv": 4800
  //   },
  //   {
  //     "name": "Page F",
  //     "pv": 3800
  //   },
  //   {
  //     "name": "Page G",
  //     "pv": 4300
  //   }
  // ]

  const getReview = async (id) => {
    const response = await API.get(`/api/review/${id}`);
    console.log(response);
    setReviews(response.data.reviewList)
  }

  useEffect(() => {
    getReview(id);
  }, [id])

  return (
    <>
      <h1>리뷰 페이지!</h1>

      <Link
        to={`/project/${id}/opinions/review/post`}
      >
        <button>🖍</button>
      </Link>
  
      {reviews.map((review, idx) => (
        <div key={idx}>
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
          {/* <Tooltip /> */}
          {/* <Legend /> */}
        </div>
      ))}
    </>
  )
}

export default ReviewPage;