package uk.ac.cardiff.mma.application.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uk.ac.cardiff.mma.application.repository.ProjectRepositoryJDBC;

@Controller
public class ProjectController {
    @Autowired
    ProjectRepositoryJDBC projectRepositoryJDBC;

    @PostMapping("/superadmin/createProject")
    public ResponseEntity createProject(@RequestBody String projectInfoJSON) {
        JSONObject projectInfo = new JSONObject(projectInfoJSON);

        String code = projectInfo.getString("code");
        String name = projectInfo.getString("name");

        return projectRepositoryJDBC.createProject(code, name);
    }
}
