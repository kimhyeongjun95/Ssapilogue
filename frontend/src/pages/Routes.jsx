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
      <Route path="/project/:projectId/editsurvey" element={<EditSurveyPage />} />

      {/* review에 객관식 주관식 nesting 예정 */}
      <Route path="/project/:projectId/review" element={<ReviewPage />} />
      <Route path="/project/:projectId/review/post" element={<PostReviewPage />} />
      

      <Route path="/project/:projectId/report" element={<ReportPage />} />
      <Route path="/project/:projectId/report/post" element={<PostReportPage />} />
      <Route path="/project/:projectId/report/:reportId" element={<ReportDetailPage />} />

    </ReactRouterRoutes>
  )
}
export default Routes;