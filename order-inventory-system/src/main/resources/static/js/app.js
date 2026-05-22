function showModule(element) {

    const module = element.getAttribute("data-module");

    // Hide all sections

    const sections = document.querySelectorAll(".endpoint-section");

    sections.forEach(section => {
        section.style.display = "none";
    });

    // Convert URL to ID

    const sectionId = module.replace("/", "");

    // Find selected section

    const selectedSection = document.getElementById(sectionId);

    // Show section

    if (selectedSection) {

        selectedSection.style.display = "block";

        // Smooth scroll

        selectedSection.scrollIntoView({

            behavior: "smooth"
        });
    }
}