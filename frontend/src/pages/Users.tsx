import { AppLayout } from "@/components/app-layout";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import {
  Table, TableBody, TableCell, TableHead, TableHeader, TableRow,
} from "@/components/ui/table";
import { UserPlus, Search, Filter, Mail, Phone, MoreHorizontal, Building2, Users as UsersIcon, ShieldCheck, UserCheck } from "lucide-react";

type Role = "Admin" | "Manager" | "Mechanic" | "Receptionist";
type Status = "Active" | "On leave" | "Inactive";

const employees: {
  id: string; name: string; email: string; phone: string;
  role: Role; team: string; status: Status; joined: string;
}[] = [
  { id: "EMP-0421", name: "Jordan Mills",     email: "jordan.mills@acme-workshop.com", phone: "+1 (415) 555-2310", role: "Admin",        team: "HQ",          status: "Active",   joined: "Mar 2021" },
  { id: "EMP-0422", name: "Priya Raman",      email: "priya.raman@acme-workshop.com",  phone: "+1 (415) 555-2188", role: "Manager",      team: "Service Bay", status: "Active",   joined: "Aug 2021" },
  { id: "EMP-0423", name: "Marcus Webb",      email: "marcus.webb@acme-workshop.com",  phone: "+1 (415) 555-7702", role: "Mechanic",     team: "Engine",      status: "Active",   joined: "Jan 2022" },
  { id: "EMP-0424", name: "Sara Okafor",      email: "sara.okafor@acme-workshop.com",  phone: "+1 (415) 555-1129", role: "Mechanic",     team: "Bodywork",    status: "On leave", joined: "Jun 2022" },
  { id: "EMP-0425", name: "Diego Hernández",  email: "diego.h@acme-workshop.com",      phone: "+1 (415) 555-8843", role: "Mechanic",     team: "Tires",       status: "Active",   joined: "Sep 2022" },
  { id: "EMP-0426", name: "Lina Park",        email: "lina.park@acme-workshop.com",    phone: "+1 (415) 555-3360", role: "Receptionist", team: "Front Desk",  status: "Active",   joined: "Feb 2023" },
  { id: "EMP-0427", name: "Tom Becker",       email: "tom.becker@acme-workshop.com",   phone: "+1 (415) 555-9921", role: "Mechanic",     team: "Electrical",  status: "Inactive", joined: "Apr 2023" },
];

const roleCls: Record<Role, string> = {
  Admin:        "bg-primary/10 text-primary border-primary/20",
  Manager:      "bg-accent/15 text-accent border-accent/30",
  Mechanic:     "bg-secondary/10 text-secondary border-secondary/20",
  Receptionist: "bg-muted text-foreground border-border",
};
const statusCls: Record<Status, string> = {
  Active:    "bg-success/10 text-success border-success/20",
  "On leave":"bg-warning/15 text-warning-foreground border-warning/30",
  Inactive:  "bg-muted text-muted-foreground border-border",
};

const stats = [
  { label: "Employees",    value: "47",  icon: UsersIcon },
  { label: "Active today", value: "39",  icon: UserCheck },
  { label: "Roles",        value: "4",   icon: ShieldCheck },
  { label: "Locations",    value: "2",   icon: Building2 },
];

const initials = (name: string) => name.split(" ").map((n) => n[0]).slice(0, 2).join("").toUpperCase();

const Users = () => {
  return (
    <AppLayout
      title="User Management"
      description="Provision employee accounts, assign workshop roles and audit access — managed by the Enterprise system."
      actions={
        <>
          <Button variant="outline"><Building2 className="mr-2 h-4 w-4" /> Enterprise sync</Button>
          <Button className="bg-primary text-primary-foreground hover:bg-secondary">
            <UserPlus className="mr-2 h-4 w-4" /> Invite employee
          </Button>
        </>
      }
    >
      {/* Enterprise banner */}
      <Card className="mb-6 border-accent/30 bg-gradient-to-r from-accent/10 via-secondary/5 to-transparent p-5">
        <div className="flex flex-wrap items-center gap-4">
          <div className="flex h-11 w-11 items-center justify-center rounded-lg bg-gradient-accent shadow-glow">
            <Building2 className="h-5 w-5 text-primary-foreground" />
          </div>
          <div className="flex-1 min-w-[220px]">
            <div className="font-display text-base font-semibold">Synced with Enterprise · Acme Workshop Group</div>
            <div className="text-sm text-muted-foreground">User identities and permissions are governed by the Enterprise directory. Last sync: 2 minutes ago.</div>
          </div>
          <Button variant="outline" size="sm">Open Enterprise console</Button>
        </div>
      </Card>

      {/* Stats */}
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4 mb-6">
        {stats.map((s) => (
          <Card key={s.label} className="p-5 bg-surface-elevated border-border shadow-sm">
            <div className="flex items-center gap-4">
              <div className="flex h-11 w-11 items-center justify-center rounded-lg bg-muted">
                <s.icon className="h-5 w-5 text-accent" />
              </div>
              <div>
                <div className="text-xs uppercase tracking-wider text-muted-foreground">{s.label}</div>
                <div className="font-display text-2xl font-bold">{s.value}</div>
              </div>
            </div>
          </Card>
        ))}
      </div>

      {/* Table */}
      <Card className="border-border bg-surface-elevated shadow-sm overflow-hidden">
        <div className="flex flex-wrap items-center gap-3 border-b border-border p-4">
          <div className="relative flex-1 min-w-[220px]">
            <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
            <Input placeholder="Search employees…" className="pl-9 h-10 bg-muted/40 border-transparent" />
          </div>
          <Button variant="outline" size="sm"><Filter className="mr-2 h-4 w-4" /> Role</Button>
          <Button variant="outline" size="sm"><Filter className="mr-2 h-4 w-4" /> Team</Button>
          <Button variant="outline" size="sm">Status: All</Button>
        </div>

        <div className="overflow-x-auto">
          <Table>
            <TableHeader>
              <TableRow className="bg-muted/40 hover:bg-muted/40">
                <TableHead>Employee</TableHead>
                <TableHead>Contact</TableHead>
                <TableHead>Role</TableHead>
                <TableHead>Team</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Joined</TableHead>
                <TableHead className="w-[40px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {employees.map((e) => (
                <TableRow key={e.id} className="group">
                  <TableCell>
                    <div className="flex items-center gap-3">
                      <Avatar className="h-9 w-9">
                        <AvatarFallback className="bg-secondary text-secondary-foreground text-xs font-semibold">
                          {initials(e.name)}
                        </AvatarFallback>
                      </Avatar>
                      <div>
                        <div className="font-medium leading-tight">{e.name}</div>
                        <div className="font-mono text-xs text-muted-foreground">{e.id}</div>
                      </div>
                    </div>
                  </TableCell>
                  <TableCell>
                    <div className="flex flex-col gap-0.5 text-xs">
                      <span className="inline-flex items-center gap-1.5 text-foreground">
                        <Mail className="h-3 w-3 text-muted-foreground" /> {e.email}
                      </span>
                      <span className="inline-flex items-center gap-1.5 text-muted-foreground">
                        <Phone className="h-3 w-3" /> {e.phone}
                      </span>
                    </div>
                  </TableCell>
                  <TableCell>
                    <Badge variant="outline" className={`font-medium ${roleCls[e.role]}`}>{e.role}</Badge>
                  </TableCell>
                  <TableCell className="text-muted-foreground">{e.team}</TableCell>
                  <TableCell>
                    <Badge variant="outline" className={`font-medium ${statusCls[e.status]}`}>
                      <span className={`mr-1.5 inline-block h-1.5 w-1.5 rounded-full ${
                        e.status === "Active" ? "bg-success" : e.status === "On leave" ? "bg-warning" : "bg-muted-foreground"
                      }`} />
                      {e.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="text-muted-foreground">{e.joined}</TableCell>
                  <TableCell>
                    <Button variant="ghost" size="icon" className="h-8 w-8 opacity-0 group-hover:opacity-100">
                      <MoreHorizontal className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>

        <div className="flex items-center justify-between border-t border-border p-4 text-sm text-muted-foreground">
          <span>Showing 7 of 47 employees</span>
          <div className="flex gap-2">
            <Button variant="outline" size="sm" disabled>Previous</Button>
            <Button variant="outline" size="sm">Next</Button>
          </div>
        </div>
      </Card>
    </AppLayout>
  );
};

export default Users;
