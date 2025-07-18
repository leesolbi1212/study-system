package com.study.management.controller;

import com.study.management.entity.Study;
import com.study.management.entity.User;
import com.study.management.service.StudyService;
import com.study.management.service.StudyApplicationService;
import com.study.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;
    private final StudyApplicationService applicationService;
    private final UserService userService;

    @GetMapping("/list")
    public String listStudies(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) String keyword,
                              Model model) {

        List<Study> studies;
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            studies = studyService.searchStudies(keyword, page, size);
            totalCount = studyService.getSearchStudyCount(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            studies = studyService.getAllStudies(page, size);
            totalCount = studyService.getTotalStudyCount();
        }

        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("studies", studies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);

        return "studies/list";
    }

    @GetMapping("/detail/{studyId}")
    public String studyDetail(@PathVariable Long studyId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("[DEBUG] 인증 객체: " + auth);
        System.out.println("[DEBUG] 인증된 사용자명: " + auth.getName());
        Long userId = null;

        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User user = userService.findByUsername(auth.getName());
            if (user != null) {
                userId = user.getUserId();
            }
        }

        Study study = studyService.getStudyWithApplicationStatus(studyId, userId);
        model.addAttribute("study", study);

        return "studies/detail";
    }

    @GetMapping("/create")
    public String createStudyForm(Model model) {
        model.addAttribute("study", new Study());
        return "studies/create";
    }

    @PostMapping("/create")
    public String createStudy(@Valid @ModelAttribute Study study,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "studies/create";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        study.setCreatorId(user.getUserId());

        studyService.createStudy(study);
        redirectAttributes.addFlashAttribute("success", "Study created successfully!");

        return "redirect:/studies/list";
    }

    @PostMapping("/apply/{studyId}")
    public String applyToStudy(@PathVariable Long studyId,
                               RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());

            applicationService.applyToStudy(studyId, user.getUserId());
            redirectAttributes.addFlashAttribute("success", "Application submitted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/studies/detail/" + studyId;
    }

    @GetMapping("/edit/{studyId}")
    public String editStudyForm(@PathVariable Long studyId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Study study = studyService.getStudyById(studyId);
        if (!study.getCreatorId().equals(user.getUserId())) {
            return "redirect:/studies/list";
        }

        model.addAttribute("study", study);
        return "studies/edit";
    }

    @PostMapping("/edit/{studyId}")
    public String editStudy(@PathVariable Long studyId,
                            @Valid @ModelAttribute Study study,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "studies/edit";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Study existingStudy = studyService.getStudyById(studyId);
        if (!existingStudy.getCreatorId().equals(user.getUserId())) {
            return "redirect:/studies/list";
        }

        study.setStudyId(studyId);
        study.setCreatorId(user.getUserId());
        studyService.updateStudy(study);

        redirectAttributes.addFlashAttribute("success", "Study updated successfully!");
        return "redirect:/studies/detail/" + studyId;
    }

    @PostMapping("/delete/{studyId}")
    public String deleteStudy(@PathVariable Long studyId,
                              RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Study study = studyService.getStudyById(studyId);
        if (!study.getCreatorId().equals(user.getUserId())) {
            return "redirect:/studies/list";
        }

        studyService.deleteStudy(studyId);
        redirectAttributes.addFlashAttribute("success", "Study deleted successfully!");

        return "redirect:/mypage";
    }
}