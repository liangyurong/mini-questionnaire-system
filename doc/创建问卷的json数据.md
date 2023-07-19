
post: localhost:8888/survey/createQuestionnaire

```text
{
  "title": "问卷题目",
  "description": "问卷描述",
  "questions": [
    {
      "code": 0,
      "name": "单选题001",
      "options": [
        {
          "myOption": "001选项1"
        },
        {
          "myOption": "001选项2"
        },
        {
          "myOption": "001选项3"
        }
      ]
    },
    {
      "code": 1,
      "name": "多选题002",
      "options": [
        {
          "myOption": "002选项1"
        },
        {
          "myOption": "002选项2"
        },
        {
          "myOption": "002选项3"
        }
      ]
    },
    {
      "code": 2,
      "name": "单项填空题003"
    }
  ]
}
```