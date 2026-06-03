package parametricbridge.bizdev.app.data.repository

import parametricbridge.bizdev.app.data.model.ServiceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalTime

class ServiceRepository {
    private val services: List<ServiceModel> = listOf(
        ServiceModel(
            id = 1,
            name = "Strategic Planning Session",
            description = "Comprehensive business strategy workshop covering market positioning, competitive landscape, and long-term growth roadmap development with executive leadership.",
            price = 2400.0,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(14, 0)),
            imageUrl = "https://images.unsplash.com/photo-1552664730-d307ca884978?w=800",
            category = "Strategic Planning",
            durationMinutes = 180,
            features = listOf(
                "Executive facilitation by senior partner",
                "Market analysis and benchmarking",
                "3-year strategic roadmap document",
                "Competitor positioning matrix",
                "Action plan with KPIs"
            )
        ),
        ServiceModel(
            id = 2,
            name = "Organizational Design Consultation",
            description = "Expert analysis and redesign of your organizational structure to improve clarity, accountability, and operational efficiency across all departments.",
            price = 3200.0,
            availableTime = listOf(LocalTime.of(10, 0), LocalTime.of(15, 0)),
            imageUrl = "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?w=800",
            category = "Organizational Development",
            durationMinutes = 240,
            features = listOf(
                "Current-state org assessment",
                "Role clarity and RACI mapping",
                "Future-state design blueprint",
                "Transition roadmap",
                "Stakeholder communication plan"
            )
        ),
        ServiceModel(
            id = 3,
            name = "Management Process Audit",
            description = "In-depth review of existing management processes to identify inefficiencies, redundancies, and opportunities for streamlining across business operations.",
            price = 1800.0,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(13, 0)),
            imageUrl = "https://images.unsplash.com/photo-1556761175-b413da4baf72?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NTV8fE1hbmFnZW1lbnR8ZW58MHx8MHx8fDA%3D",
            category = "Performance & Efficiency",
            durationMinutes = 120,
            features = listOf(
                "Process mapping and documentation",
                "Bottleneck identification",
                "Efficiency scoring framework",
                "Prioritized improvement list",
                "Implementation timeline"
            )
        ),
        ServiceModel(
            id = 4,
            name = "Leadership Development Workshop",
            description = "Immersive leadership training program designed to elevate managerial capabilities, strengthen decision-making, and build high-performance leadership culture.",
            price = 2800.0,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(14, 0)),
            imageUrl = "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800",
            category = "Leadership & Coaching",
            durationMinutes = 360,
            features = listOf(
                "360-degree leadership assessment",
                "Situational leadership models",
                "Team motivation frameworks",
                "Conflict resolution techniques",
                "Personal development plan"
            )
        ),
        ServiceModel(
            id = 5,
            name = "Business Transformation Roadmap",
            description = "End-to-end transformation planning service covering digital adoption, process modernization, and cultural change management for sustainable growth.",
            price = 4500.0,
            availableTime = listOf(LocalTime.of(10, 0), LocalTime.of(15, 0)),
            imageUrl = "https://images.unsplash.com/photo-1521737852567-6949f3f9f2b5?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NjN8fE1hbmFnZW1lbnR8ZW58MHx8MHx8fDA%3D",
            category = "Strategic Planning",
            durationMinutes = 300,
            features = listOf(
                "Digital readiness assessment",
                "Transformation vision and goals",
                "Phased implementation plan",
                "Change impact analysis",
                "Benefits realization framework"
            )
        ),
        ServiceModel(
            id = 6,
            name = "Operational Efficiency Review",
            description = "Detailed operational assessment identifying waste, cost reduction opportunities, and performance improvements across your core business functions.",
            price = 2200.0,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(11, 0), LocalTime.of(14, 0)),
            imageUrl = "https://plus.unsplash.com/premium_photo-1661767467261-4a4bed92a507?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NjV8fE1hbmFnZW1lbnR8ZW58MHx8MHx8fDA%3D",
            category = "Performance & Efficiency",
            durationMinutes = 180,
            features = listOf(
                "Operations benchmarking study",
                "Value stream mapping",
                "Cost reduction opportunities",
                "Process standardization plan",
                "Performance metrics dashboard"
            )
        ),
        ServiceModel(
            id = 7,
            name = "HR Strategy Consulting",
            description = "Strategic HR advisory covering talent acquisition, retention frameworks, compensation benchmarking, and workforce planning aligned with business goals.",
            price = 1950.0,
            availableTime = listOf(LocalTime.of(10, 0), LocalTime.of(15, 0)),
            imageUrl = "https://images.unsplash.com/photo-1521737604893-d14cc237f11d?w=800",
            category = "Organizational Development",
            durationMinutes = 150,
            features = listOf(
                "Talent strategy alignment",
                "Compensation benchmarking",
                "Employee experience audit",
                "Workforce planning model",
                "HR systems evaluation"
            )
        ),
        ServiceModel(
            id = 8,
            name = "Change Management Program",
            description = "Structured change management engagement to guide your organization through major transitions with minimal disruption and maximum employee buy-in.",
            price = 3600.0,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(14, 0)),
            imageUrl = "https://images.unsplash.com/photo-1590402494693-bd0499aefe00?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8ODN8fE1hbmFnZW1lbnR8ZW58MHx8MHx8fDA%3D",
            category = "Organizational Development",
            durationMinutes = 240,
            features = listOf(
                "Change readiness assessment",
                "Stakeholder engagement strategy",
                "Communication planning",
                "Resistance management",
                "Adoption tracking metrics"
            )
        ),
        ServiceModel(
            id = 9,
            name = "Performance Management Framework",
            description = "Design and implementation of a robust performance management system with clear KPIs, review cycles, and feedback mechanisms that drive results.",
            price = 2600.0,
            availableTime = listOf(LocalTime.of(10, 0), LocalTime.of(13, 0)),
            imageUrl = "https://images.unsplash.com/photo-1553877522-43269d4ea984?w=800",
            category = "Performance & Efficiency",
            durationMinutes = 180,
            features = listOf(
                "KPI framework design",
                "Goal-setting methodology (OKRs)",
                "Review cycle templates",
                "Manager training guide",
                "Recognition program design"
            )
        ),
        ServiceModel(
            id = 10,
            name = "Executive Coaching Session",
            description = "One-on-one executive coaching focused on leadership effectiveness, strategic thinking, communication, and navigating complex organizational challenges.",
            price = 800.0,
            availableTime = listOf(LocalTime.of(8, 0), LocalTime.of(12, 0), LocalTime.of(17, 0)),
            imageUrl = "https://images.unsplash.com/photo-1560264418-c4445382edbc?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTAxfHxNYW5hZ2VtZW50fGVufDB8fDB8fHww",
            category = "Leadership & Coaching",
            durationMinutes = 90,
            features = listOf(
                "Personalized leadership assessment",
                "Goal setting and accountability",
                "Strategic thinking exercises",
                "Executive presence coaching",
                "Post-session action commitments"
            )
        ),
        ServiceModel(
            id = 11,
            name = "Stakeholder Engagement Strategy",
            description = "Develop a comprehensive stakeholder mapping and engagement plan to build trust, manage expectations, and create aligned support for key initiatives.",
            price = 1600.0,
            availableTime = listOf(LocalTime.of(10, 0), LocalTime.of(14, 0)),
            imageUrl = "https://images.unsplash.com/photo-1600880292203-757bb62b4baf?w=800",
            category = "Strategic Planning",
            durationMinutes = 120,
            features = listOf(
                "Stakeholder mapping matrix",
                "Influence and interest analysis",
                "Engagement tactics by segment",
                "Communication calendar",
                "Feedback loop design"
            )
        ),
        ServiceModel(
            id = 12,
            name = "Corporate Culture Assessment",
            description = "Comprehensive evaluation of organizational culture, values alignment, and employee engagement to build a high-performance workplace culture.",
            price = 2100.0,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(15, 0)),
            imageUrl = "https://images.unsplash.com/photo-1630672790237-38eeb57cb60b?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTA5fHxNYW5hZ2VtZW50fGVufDB8fDB8fHww",
            category = "Organizational Development",
            durationMinutes = 150,
            features = listOf(
                "Culture diagnostic survey",
                "Values alignment analysis",
                "Engagement benchmark report",
                "Culture improvement initiatives",
                "Leadership behavior assessment"
            )
        ),
    )

    fun observeAll(): Flow<List<ServiceModel>> {
        return flowOf(services)
    }

    fun observeById(id: Int): Flow<ServiceModel?> {
        val service = services.firstOrNull { service -> service.id == id }
        return flowOf(service)
    }

    fun getById(id: Int): ServiceModel? {
        return services.firstOrNull { service -> service.id == id }
    }
}
