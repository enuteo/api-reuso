import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { Wrench, ArrowRight, Shield, Boxes, Users } from "lucide-react";

const Login = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setTimeout(() => navigate("/app"), 600);
  };

  return (
    <div className="min-h-screen grid lg:grid-cols-2">
      {/* Brand panel */}
      <aside className="relative hidden lg:flex flex-col justify-between overflow-hidden bg-gradient-hero p-12 text-primary-foreground">
        <div className="absolute inset-0 grid-bg opacity-40" />
        <div className="absolute -bottom-40 -right-32 h-96 w-96 rounded-full bg-accent/30 blur-3xl" />
        <div className="absolute -top-32 -left-20 h-80 w-80 rounded-full bg-accent/20 blur-3xl" />

        <div className="relative flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-primary-foreground/10 backdrop-blur ring-1 ring-primary-foreground/20">
            <Wrench className="h-5 w-5" strokeWidth={2.5} />
          </div>
          <div>
            <div className="font-display text-xl font-bold leading-none">Forge</div>
            <div className="text-xs uppercase tracking-widest text-primary-foreground/60">Workshop OS</div>
          </div>
        </div>

        <div className="relative space-y-8">
          <div>
            <div className="inline-flex items-center gap-2 rounded-full bg-primary-foreground/10 px-3 py-1 text-xs font-medium ring-1 ring-primary-foreground/20">
              <span className="h-1.5 w-1.5 rounded-full bg-success" />
              Enterprise · Workshop Edition
            </div>
            <h2 className="mt-6 font-display text-4xl font-bold leading-[1.1] xl:text-5xl">
              Run your workshop with the precision of a finely tuned machine.
            </h2>
            <p className="mt-5 max-w-md text-primary-foreground/70">
              Inventory, employees, work orders and reporting — unified in one
              calm, dependable command center.
            </p>
          </div>

          <ul className="space-y-3 text-sm">
            {[
              { icon: Boxes, text: "Real-time stock & low-level alerts" },
              { icon: Users, text: "Employee roles managed from Enterprise" },
              { icon: Shield, text: "Audited, secure and SOC 2 ready" },
            ].map((f) => (
              <li key={f.text} className="flex items-center gap-3">
                <div className="flex h-8 w-8 items-center justify-center rounded-md bg-primary-foreground/10 ring-1 ring-primary-foreground/20">
                  <f.icon className="h-4 w-4" />
                </div>
                <span className="text-primary-foreground/85">{f.text}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="relative text-xs text-primary-foreground/50">
          © {new Date().getFullYear()} Forge Systems · v4.2
        </div>
      </aside>

      {/* Form panel */}
      <section className="flex items-center justify-center p-6 sm:p-12 bg-background">
        <div className="w-full max-w-md animate-fade-in">
          <div className="mb-10 lg:hidden flex items-center gap-2">
            <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-gradient-accent">
              <Wrench className="h-5 w-5 text-primary-foreground" strokeWidth={2.5} />
            </div>
            <span className="font-display text-xl font-bold">Forge</span>
          </div>

          <h1 className="font-display text-3xl font-bold tracking-tight">Welcome back</h1>
          <p className="mt-2 text-muted-foreground">
            Sign in to your workshop dashboard.
          </p>

          <form onSubmit={onSubmit} className="mt-8 space-y-5">
            <div className="space-y-2">
              <Label htmlFor="email">Work email</Label>
              <Input id="email" type="email" placeholder="you@acme-workshop.com" required defaultValue="jordan.mills@acme-workshop.com" className="h-11" />
            </div>

            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <Label htmlFor="password">Password</Label>
                <a href="#" className="text-xs font-medium text-accent hover:underline">
                  Forgot password?
                </a>
              </div>
              <Input id="password" type="password" placeholder="••••••••••" required defaultValue="demo-password" className="h-11" />
            </div>

            <div className="flex items-center gap-2">
              <Checkbox id="remember" defaultChecked />
              <Label htmlFor="remember" className="text-sm font-normal text-muted-foreground">
                Keep me signed in on this device
              </Label>
            </div>

            <Button type="submit" disabled={loading} className="h-11 w-full bg-primary text-primary-foreground hover:bg-secondary group">
              {loading ? "Signing in…" : "Sign in"}
              <ArrowRight className="ml-2 h-4 w-4 transition-transform group-hover:translate-x-0.5" />
            </Button>

            <div className="relative my-2">
              <div className="absolute inset-0 flex items-center"><span className="w-full border-t border-border" /></div>
              <div className="relative flex justify-center"><span className="bg-background px-3 text-xs uppercase tracking-widest text-muted-foreground">or</span></div>
            </div>

            <Button type="button" variant="outline" className="h-11 w-full">
              <Shield className="mr-2 h-4 w-4" />
              Continue with Enterprise SSO
            </Button>
          </form>

          <p className="mt-8 text-center text-sm text-muted-foreground">
            Need an account? Contact your{" "}
            <Link to="/app/enterprise" className="font-medium text-accent hover:underline">
              Enterprise administrator
            </Link>
            .
          </p>
        </div>
      </section>
    </div>
  );
};

export default Login;
