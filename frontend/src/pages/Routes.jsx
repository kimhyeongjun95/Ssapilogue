import { Route, Routes as ReactRouterRoutes } from 'react-router-dom';
import HomePage from './HomePage';
import SignUpPage from './SignUpPage';
import SignInPage from './SignInPage'
import ProfilePage from './ProfilePage'
import ChangeInfoPage from './ChangeInfoPage'
import PostProjectPage from './PostProjectPage';
import PostSurveyPage from './PostSurveyPage'
import EditSurveyPage from './EditSurveyPage'
import ProjectDetailPage from './ProjectDetailPage'
import ReviewPage from './ReviewPage'
import PostReviewPage from './PostReviewPage'
import ReportPage from './ReportPage'
import PostReportPage from './PostReportPage'
import ReportDetailPage from './ReportDetailPage'
import OpinionPage from './OpinionPage';
import EditReportPage from './EditReportPage';
import EditProjectPage from './EditProjectPage';

const Routes = () => {
  return (
    <ReactRouterRoutes>
      <Route path='/' element={<HomePage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/signin" element={<SignInPage />} />
      <Route path="/profile" element={<ProfilePage />} />
      <Route path="/changeinfo" element={<ChangeInfoPage />} />

      <Route path="/project/post" element={<PostProjectPage />} />
      <Route path="/project/survey" element={<PostSurveyPage />} />

      <Route path="/project/:projectId" element={<ProjectDetailPage />} />
      <Route path="/project/:projectId/edit" element={<EditProjectPage />} />
      <Route path="/project/:projectId/survey/edit" element={<EditSurveyPage />} />

      <Route path="/project/:projectId/opinions" element={<OpinionPage />} >
        <Route path="review" element={<ReviewPage />} />
        <Route path="report" element={<ReportPage />} />
      </Route>
      <Route path="/project/:projectId/opinions/review/post" element={<PostReviewPage />} />
      <Route path="/project/:projectId/opinions/report/post" element={<PostReportPage />} />
      <Route path="/project/:projectId/opinions/report/:reportId" element={<ReportDetailPage />} />
      <Route path="/project/:projectId/opinions/report/:reportId/edit" element={<EditReportPage />} />

    </ReactRouterRoutes>
  )
}
export default Routes;