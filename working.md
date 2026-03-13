# 工作记录 - OpenCode Android 客户端

## 2026-03-13

### Question 功能实现（参照 iOS 客户端）

在 `feature/question-support` 分支上完成了完整的 question 功能：

**数据层**
- `data/model/Question.kt` — `QuestionOption`、`QuestionInfo`、`QuestionRequest` 数据模型
- `data/api/OpenCodeApi.kt` — 3 个新端点：`GET /question`、`POST /question/{id}/reply`、`POST /question/{id}/reject`
- `data/repository/OpenCodeRepository.kt` — `getPendingQuestions()`、`replyQuestion()`、`rejectQuestion()`

**ViewModel 层**
- `AppState` 添加 `pendingQuestions: List<QuestionRequest>` 字段
- `MainViewModelSupport.kt` — `parseQuestionAskedEvent()` 解析 SSE payload（读取 `properties: JsonObject`）
- `MainViewModelSyncActions.kt` — 处理 `question.asked`、`question.replied`、`question.rejected` SSE 事件
- `MainViewModel.kt` — `loadPendingQuestions()`、`replyQuestion()`、`rejectQuestion()` 方法

**UI 层**
- `ui/chat/QuestionCardView.kt` — 完整的问题卡片 Composable，支持单选/多选/自定义输入、多题分步显示、进度指示
- `ui/chat/ChatScreen.kt` — 集成 `QuestionCardView`，过滤当前 session 的问题

**测试**
- `QuestionTest.kt` — 5 个单元测试，全部通过

**构建验证**
- `./gradlew assembleDebug` — BUILD SUCCESSFUL
- `./gradlew testDebugUnitTest` — 5/5 tests pass, 0 failures
