# System Analysis Diagrams

This directory contains UML diagrams documenting the system architecture and processes.

## Files Overview

| File | Type | Description |
|------|------|-------------|
| `sequence-message.puml` | PlantUML Source | Message sending process code |
| `sequence-registration.puml` | PlantUML Source | User registration process code |
| `sequence-registration.png` | Rendered Diagram | User registration visualization |
| `SequenceDiagram.png` | Rendered Diagram | General sequence diagram |

## How to Use

### Viewing Diagrams
- **PNG files**: Ready-to-view rendered diagrams
- **PUML files**: Source code for PlantUML diagrams

### Generating New Diagrams
1. Install PlantUML or use online editor
2. Edit `.puml` files as needed
3. Generate PNG output:
   ```bash
   java -jar plantuml.jar sequence-message.puml