import { AppLayout } from "@/components/app-layout";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import { Boxes, Users, ArrowRight, Wrench, Activity, AlertTriangle } from "lucide-react";

const Overview = () => (
  <AppLayout
    title="Workshop Overview"
    description="A quick pulse of operations. Jump into stock or user management to dig deeper."
  >
    <div className="grid gap-4 md:grid-cols-3 mb-6">
      {[
        { label: "Open work orders", value: "18", icon: Wrench, hint: "5 due today" },
        { label: "Bay utilization", value: "82%", icon: Activity, hint: "+6% vs last week" },
        { label: "Critical alerts",  value: "3",   icon: AlertTriangle, hint: "Stock & maintenance" },
      ].map((s) => (
        <Card key={s.label} className="p-6 bg-surface-elevated border-border shadow-sm">
          <div className="flex items-start justify-between">
            <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-muted">
              <s.icon className="h-5 w-5 text-accent" />
            </div>
          </div>
          <div className="mt-4">
            <div className="text-xs uppercase tracking-wider text-muted-foreground">{s.label}</div>
            <div className="mt-1 font-display text-3xl font-bold">{s.value}</div>
            <div className="mt-1 text-xs text-muted-foreground">{s.hint}</div>
          </div>
        </Card>
      ))}
    </div>

    <div className="grid gap-4 md:grid-cols-2">
      <Link to="/app/stock">
        <Card className="p-6 bg-surface-elevated border-border shadow-sm hover:shadow-elev hover:border-accent/40 transition-all group h-full">
          <div className="flex items-center gap-4">
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-gradient-accent shadow-glow">
              <Boxes className="h-6 w-6 text-primary-foreground" />
            </div>
            <div className="flex-1">
              <div className="font-display text-lg font-semibold">Stock Management</div>
              <div className="text-sm text-muted-foreground">Inventory, alerts and replenishment.</div>
            </div>
            <ArrowRight className="h-5 w-5 text-muted-foreground transition-transform group-hover:translate-x-1" />
          </div>
        </Card>
      </Link>
      <Link to="/app/users">
        <Card className="p-6 bg-surface-elevated border-border shadow-sm hover:shadow-elev hover:border-accent/40 transition-all group h-full">
          <div className="flex items-center gap-4">
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-gradient-accent shadow-glow">
              <Users className="h-6 w-6 text-primary-foreground" />
            </div>
            <div className="flex-1">
              <div className="font-display text-lg font-semibold">User Management</div>
              <div className="text-sm text-muted-foreground">Employees, roles and Enterprise sync.</div>
            </div>
            <ArrowRight className="h-5 w-5 text-muted-foreground transition-transform group-hover:translate-x-1" />
          </div>
        </Card>
      </Link>
    </div>

    <div className="mt-8 flex justify-end">
      <Button variant="outline" asChild>
        <Link to="/login">View login screen</Link>
      </Button>
    </div>
  </AppLayout>
);

export default Overview;
